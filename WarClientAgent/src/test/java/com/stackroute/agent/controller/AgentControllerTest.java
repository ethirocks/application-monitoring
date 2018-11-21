package com.stackroute.agent.controller;
import com.stackroute.agent.service.CpuUsage;
import com.stackroute.agent.service.NetworkMetrics;
import com.stackroute.agent.service.RamUtilization;
import com.stackroute.agent.service.ThreadsIndicator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
public class AgentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThreadsIndicator threadsIndicator;

    @MockBean
    private NetworkMetrics networkMetrics;

    @MockBean
    private CpuUsage cpuUsage;

    @MockBean
    private RamUtilization ramUtilization;

    @InjectMocks
    private AgentController agentController;


    @Test
    public void getThreads() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders.get("/threads").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void getThreadsFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/threads1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void getNetworkInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/network").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void getNetworkInfoFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/network1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void cpuCores() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders.get("/cpucores").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }


    @Test
    public void cpuTemperature() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cputemp").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void cpuUsageFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cpuusage1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void cpuCoresFailure() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders.get("/cpucores1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }


    @Test
    public void cpuTemperatureFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cputemp1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void cpuUsage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cpuusage").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void ramUtilization() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders.get("/memory").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void ramUtilizationFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/memory1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }




}
