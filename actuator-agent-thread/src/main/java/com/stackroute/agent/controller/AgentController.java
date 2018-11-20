package com.stackroute.agent.controller;
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
	public ResponseEntity<Threads> getThreads(){
		return new ResponseEntity<Threads>(threadsIndicator.getThreads(), HttpStatus.OK);
	}

	@GetMapping("/memory")
	public ArrayList<Long> freeRam(){

		return ramUtilization.ramMemory();
	}

	@GetMapping("/cputemp")
	public double cpuTemperature() {
		return cpuUsage.cpuTemperature();
	}

	@GetMapping("/cpucores")
	public double cpuCores() {
		return cpuUsage.cpuCore();
	}

	@GetMapping("/cpuusage")
	public double cpuUsage() {
		return cpuUsage.cpuUse();
	}

	@GetMapping("/network")
    public ResponseEntity<?> getNetworkInfo() throws InterruptedException, SocketException {
        return new ResponseEntity<Map>(networkMetrics.getNetworkInfo(),HttpStatus.OK);

    }

}
