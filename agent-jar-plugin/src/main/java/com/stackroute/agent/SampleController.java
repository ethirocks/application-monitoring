package com.stackroute.agent;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class SampleController {
	@Autowired
	DiskSpaceHealthIndicator health;
	@Autowired
	CustomEndpoint custom;

	@Autowired
	PhysicalMemory virtualMemory;

	@Autowired
	Interceptor interceptor;

	@Autowired
	ThreadsIndicator threadsIndicator;

	@Autowired
	CpuTemperature temperatures;
	@Autowired
	CpuCores cores;
	@Autowired
	CpuUsage cpuuse;
	@Autowired
	NetworkIndicator networkIndicator;

	@GetMapping("/threads")
	public ResponseEntity<ThreadMetrics> getThreads(){
		return new ResponseEntity<ThreadMetrics>(threadsIndicator.getThreads(), HttpStatus.OK);
	}

	@GetMapping("/network")
	public ResponseEntity<?> getNetworkInfo() throws InterruptedException, SocketException {
		return new ResponseEntity<Map>(networkIndicator.getNetworkInfo(),HttpStatus.OK);

	}

	@GetMapping("/cputemp")
	public double Temperature() {
		return temperatures.temperature();
	}

	@GetMapping("/cpucores")
	public double Cores() {
		return cores.cpucore();
	}

	@GetMapping("/cpuusage")
	public double Use() {
		return cpuuse.cpuuse();
	}

	@GetMapping("/memory")
	public ArrayList<Long> freeRam(){
		return virtualMemory.ramMemory();
	}
	@GetMapping("/httpMetrics")
	@ResponseBody
	public List<HttpMetrics> sendMetrics() throws IOException {

		return interceptor.getHttpMetricsList();
	}

	@GetMapping("/custom")
	public List<String> customEndPoint() {
		return custom.invoke();
	}

	@GetMapping("/")
	public String sayHello(@RequestParam(value = "name", defaultValue = "Guest") String name) {
		return "Hello " + name + "!!";
	}

	@GetMapping("/health")
	public Health sayHello() {
		return health.health();

	}

	@GetMapping("/slowApi")
	public String timeConsumingAPI(@RequestParam(value = "delay", defaultValue = "0") Integer delay)
			throws InterruptedException {
		if (delay == 0) {
			Random random = new Random();
			delay = random.nextInt(10);
		}

		TimeUnit.SECONDS.sleep(delay);
		return "Result";
	}

	@RequestMapping(value="/heapdump", method= RequestMethod.GET)
	public String getDownload(HttpServletResponse response) throws IOException, InterruptedException {

		//Getting current process id
		long pid = ProcessHandle.current().pid();
		System.out.println(pid);

		//Running terminal command through our application to create heapdump in tmp folder
		String command = "jmap -dump:live,file=/tmp/heapdumponpc.jmap "+ pid;

		Process proc = Runtime.getRuntime().exec(command);

		// Read the output

		BufferedReader reader =
				new BufferedReader(new InputStreamReader(proc.getInputStream()));

		String line = "";
		while((line = reader.readLine()) != null) {
			System.out.print(line + "\n");
		}

		proc.waitFor();

		//Getting filestream to send heapdump file over the internet
		InputStream myStream = new FileInputStream(new File("/tmp/heapdumponpc.jmap"));

		response.addHeader("Content-disposition", "attachment;filename=heapdumponpc.jmap");
		response.setContentType("jmap/bin");
		response.setStatus(200);


		IOUtils.copy(myStream, response.getOutputStream());
		response.flushBuffer();
		return "HeapDump";
	}


}
