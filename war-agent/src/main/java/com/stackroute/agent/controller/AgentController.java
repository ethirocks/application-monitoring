package com.stackroute.agent.controller;
import com.stackroute.agent.model.Metrics;
import com.stackroute.agent.model.Threads;
import com.stackroute.agent.service.CpuUsage;
import com.stackroute.agent.service.NetworkMetrics;
import com.stackroute.agent.service.RamUtilization;
import com.stackroute.agent.service.ThreadsIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Map;

@Controller
@ResponseBody
public class AgentController {
	/*@Autowired
	DiskSpaceHealthIndicator health;*/
	@Autowired
	ThreadsIndicator threadsIndicator;
	@Autowired
	RamUtilization ramUtilization;

	@Autowired
	CpuUsage cpuUsage;

	@Autowired
    NetworkMetrics networkMetrics;


	@GetMapping("/threads")
	public ResponseEntity<Metrics> getThreads(@RequestParam Integer userID,
											  @RequestParam Integer applicationID){
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(threadsIndicator.getThreads());
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);
	}

	@GetMapping("/memory")
	public ResponseEntity<Metrics> freeRam(@RequestParam Integer userID,
								   @RequestParam Integer applicationID){
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(ramUtilization.ramMemory());
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);
	}

	@GetMapping("/cputemp")
	public ResponseEntity<Metrics> cpuTemperature(@RequestParam Integer userID,
								 @RequestParam Integer applicationID) {
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(cpuUsage.cpuTemperature());
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);
	}

	@GetMapping("/cpucores")
	public ResponseEntity<Metrics> cpuCores(@RequestParam Integer userID,
											@RequestParam Integer applicationID) {
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(cpuUsage.cpuCore());
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);
	}

	@GetMapping("/cpuusage")
	public ResponseEntity<Metrics> cpuUsage(@RequestParam Integer userID,
						   @RequestParam Integer applicationID) {
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(cpuUsage.cpuUse());
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);
	}

	@GetMapping("/network")
    public ResponseEntity<Metrics> getNetworkInfo(@RequestParam Integer userID,
											@RequestParam Integer applicationID) throws InterruptedException, SocketException {
		Metrics metrics=new Metrics();
		metrics.setApplicationID(applicationID);
		metrics.setUserID(userID);
		metrics.setMetrics(networkMetrics.getNetworkInfo());
		return new ResponseEntity<Metrics>(metrics,HttpStatus.OK);

    }

}
