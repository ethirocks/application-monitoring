package com.stackroute.samplingserver.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.stackroute.domain.JmeterMetrics;
import com.stackroute.domain.KafkaDomain;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.SetupThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JmeterService {

    @Autowired
    private KafkaTemplate<String,KafkaDomain> kafkaTemplate;

    private static String TOPIC="jmeterMetrics";
//
//    private List<String> getlinks(){
//
//        Map<String, Object> root = (Map) obj;
//        Map<String, Object> paths = (Map) root.get("paths");
//        List<String> keylistpaths = new ArrayList<String>(paths.keySet());
//        System.out.println(keylistpaths);
//        return keylistpaths;
//    }
//    private String url{
//        Map<String, Object> root = (Map) obj;
//        return root.get("host").toString();
//    }
//    private Map<String,Object> getpaths(){
//        Map<String, Object> root = (Map) obj;
//        Map<String, Object> paths = (Map) root.get("paths");
//        return paths;
//    }

    public void check(String URL,String JmeterProperties,String JmeterHome,Integer userID,Integer applicationID) {
    //    String url = "http://172.23.239.222:9001/v2/api-docs";
        String currentDir = System.getProperty("user.dir");
        RestTemplate temp = new RestTemplate();
        Object obj = temp.getForObject(URL+"/v2/api-docs", Object.class);

        Map<String, Object> root = (Map) obj;
        Map<String, Object> paths = (Map) root.get("paths");
        List<String> keylistpaths = new ArrayList<String>(paths.keySet());

        String url=root.get("host").toString();

        CSVReader csvReader = null;
        Reader reader = null;
        for(int loop=10;loop<100000;loop=loop*10) {
            List<String> endpoints=keylistpaths;
            for(String endpoint:endpoints){
                Map<String, Object> requestsq = paths;
                Map<String, Object> requestst = (Map) requestsq.get(endpoint);
                List<String> requestsstring = new ArrayList<String>(requestst.keySet());
                for(String requests:requestsstring){
                    String finalendpoint = endpoint+url;
                    StandardJMeterEngine jm = new StandardJMeterEngine();
                    File file = new File(currentDir + "/example.csv");
                    if (file.delete()) {
                        System.out.println(" File deleted");
                    } else System.out.println("File doesn't exists");
                    // jmeter.properties
                    JMeterUtils.loadJMeterProperties(JmeterProperties);    // app.prop
                    JMeterUtils.initLocale();
                    JMeterUtils.setJMeterHome(JmeterHome);     //app.prop

                    HashTree hashTree = new HashTree();

                    // HTTP Sampler
                    HTTPSampler httpSampler = new HTTPSampler();
                    httpSampler.setDomain(url.split(":")[0]);
                    httpSampler.setPort(Integer.parseInt(url.split(":")[1]));
                    httpSampler.setPath(endpoint);
                    httpSampler.setMethod(requests.toUpperCase());

                    // Loop Controller
                    TestElement loopCtrl = new LoopController();
                    ((LoopController) loopCtrl).setLoops(1);
                    ((LoopController) loopCtrl).addTestElement(httpSampler);
                    ((LoopController) loopCtrl).setFirst(true);

                    // Thread Group
                    SetupThreadGroup threadGroup = new SetupThreadGroup();
                    threadGroup.setNumThreads(loop);
                    //threadGroup.setRampUp(40);
                    threadGroup.setSamplerController((LoopController) loopCtrl);

                    // Test plan
                    TestPlan testPlan = new TestPlan("MY TEST PLAN");

                    hashTree.add("testPlan", testPlan);
                    hashTree.add("loopCtrl", loopCtrl);
                    hashTree.add("threadGroup", threadGroup);
                    hashTree.add("httpSampler", httpSampler);
                    String slash = System.getProperty("file.separator");
                    HashTree testPlanTree = new HashTree();
                    testPlanTree.add(testPlan);
                    HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
                    threadGroupHashTree.add(httpSampler);
                    try {
                        SaveService.saveTree(testPlanTree, new FileOutputStream(currentDir + "/example.jmx"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Summariser summer = null;
                    String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
                    if (summariserName.length() > 0) {
                        summer = new Summariser(summariserName);
                    }
                    Map<String, String> values;
                    String logFile = currentDir + "/example.csv";
                    ResultCollector logger = new ResultCollector(summer);
                    logger.setFilename(logFile);

                    testPlanTree.add(testPlanTree.getArray()[0], logger);
                    jm.configure(testPlanTree);
                    jm.run();
                    String SAMPLE_CSV_FILE_PATH = currentDir + "/example.csv";



                    try {
                        reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                        csvReader = new CSVReader(reader);


                    } catch (Exception e) {
                        csvReader = new CSVReaderBuilder(reader).build();
                    }

                    int i = 0;
                    ArrayList<Integer> store = new ArrayList<Integer>();
                    try {
                        String[] nextRecord;

                        while ((nextRecord = csvReader.readNext()) != null) {
                            if (i > 0) {

                                store.add(Integer.valueOf(nextRecord[1]));
                            }
                            i++;
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    System.out.println(store);
                    int number = 0;
                    for (int k = 0; k < store.size() - 1; k++) {
                        number = number + store.get(k);
                    }
                    int size=number/(store.size()-1);

                    JmeterMetrics jmeterMetrics=new JmeterMetrics();
                    jmeterMetrics.setRequestMethod(requests.toUpperCase());
                    jmeterMetrics.setRequestUrl(httpSampler.getDomain());
                    jmeterMetrics.setResponseTime(size);
                    jmeterMetrics.setServerPort(httpSampler.getPort());
                    jmeterMetrics.setEndpoint(endpoint);

                    KafkaDomain kafkaDomain=new KafkaDomain();
                    kafkaDomain.setApplicationId(userID);
                    kafkaDomain.setUserId(applicationID);
                    kafkaDomain.setTime(System.currentTimeMillis());
                    kafkaDomain.setMetrics(jmeterMetrics);
                    kafkaTemplate.send(TOPIC,kafkaDomain);
                }
            }
        }
    }
}

