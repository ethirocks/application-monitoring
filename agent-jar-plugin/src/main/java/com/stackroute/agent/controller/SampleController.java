package com.stackroute.agent.controller;

import com.stackroute.agent.*;
import com.stackroute.agent.domain.Metrics;
import com.stackroute.agent.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.SocketException;
import java.util.Map;

@CrossOrigin("*")
@RestController
public class SampleController {
	@Autowired
	DiskSpaceHealthIndicator health;

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
	public ResponseEntity<Metrics> getThreads(@RequestParam Integer userID,
											  @RequestParam Integer applicationID){
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(threadsIndicator.getThreads(userID, applicationID));
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);
	}

	@GetMapping("/network")
	public ResponseEntity<Metrics> getNetworkInfo(@RequestParam Integer userID,
											@RequestParam Integer applicationID) throws InterruptedException, SocketException {
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(networkIndicator.getNetworkInfo());
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);

	}

	@GetMapping("/cputemp")
	public ResponseEntity<Metrics> Temperature(@RequestParam Integer userID,
							  @RequestParam Integer applicationID) {
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(temperatures.temperature());
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);
	}

	@GetMapping("/cpucores")
	public ResponseEntity<Metrics> Cores(@RequestParam Integer userID,
						@RequestParam Integer applicationID) {
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(cores.cpucore(userID,applicationID));
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);
	}

	@GetMapping("/cpuusage")
	public ResponseEntity<Metrics> Use(@RequestParam Integer userID,
					  @RequestParam Integer applicationID) {
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(cpuuse.cpuuse());
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);
	}

	@GetMapping("/memory")
	public ResponseEntity<Metrics> freeRam(@RequestParam Integer userID,
								   @RequestParam Integer applicationID){
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(virtualMemory.ramMemory());
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);
	}

	@GetMapping("/httpMetrics")
	@ResponseBody
	public ResponseEntity<Metrics> sendMetrics(@RequestParam Integer userID,
										 @RequestParam Integer applicationID) throws IOException {
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(interceptor.getHttpMetricsList());
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);
	}

	@GetMapping("/")
	public String sayHello(@RequestParam(value = "name", defaultValue = "Guest") String name) {
		return "Hello " + name + "!!";
	}

	@GetMapping("/health")
	public ResponseEntity<Metrics> health(@RequestParam Integer userID,
						   @RequestParam Integer applicationID) {
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(health.health());
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);


	}

//	@RequestMapping(value="/heapdump", method= RequestMethod.GET)
//	public String getDownload(HttpServletResponse response) throws IOException, InterruptedException {
//
//		//Getting current process id
//		long pid = ProcessHandle.current().pid();
//		System.out.println(pid);
//
//		//Running terminal command through our application to create heapdump in tmp folder
//		String command = "jmap -dump:live,file=/tmp/heapdumponpc.jmap "+ pid;
//
//		Process proc = Runtime.getRuntime().exec(command);
//
//		// Read the output
//
//		BufferedReader reader =
//				new BufferedReader(new InputStreamReader(proc.getInputStream()));
//
//		String line = "";
//		while((line = reader.readLine()) != null) {
//			System.out.print(line + "\n");
//		}
//
//		proc.waitFor();
//
//		//Getting filestream to send heapdump file over the internet
//		InputStream myStream = new FileInputStream(new File("/tmp/heapdumponpc.jmap"));
//
//		response.addHeader("Content-disposition", "attachment;filename=heapdumponpc.jmap");
//		response.setContentType("jmap/bin");
//		response.setStatus(200);
//
//
//		IOUtils.copy(myStream, response.getOutputStream());
//		response.flushBuffer();
//		return "HeapDump";
//	}
//

}
