package com.citihub.configr.api;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.citihub.configr.namespace.Namespace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(path = "/configuration")
public class ConfigurationController {

  private ConfigurationService configurationService;
  
  public ConfigurationController(@Autowired ConfigurationService configurationService) {
    this.configurationService = configurationService;
  }
  
  @GetMapping(path = "/**")
  public @ResponseBody Map<String, Object> getData(
      HttpServletRequest request, HttpServletResponse response) {
    return configurationService.fetchNamespaceBodyByPath(getTrimmedPath(request));
  }

  @PostMapping(consumes = {"application/json", "application/yaml", "application/yml"},
      value = "/**")
  public @ResponseBody Namespace postData(
      @RequestBody Map<String, Object> json, 
      HttpServletRequest request, HttpServletResponse response) throws IOException {
    log.info("You asked for me to POST: " + json + " to the namespace " + request.getRequestURI());

    return configurationService.storeNamespace(json, getTrimmedPath(request), false);
  }

  @PutMapping(consumes = {"application/json", "application/yaml", "application/yml"},
      value = "/**")
  public @ResponseBody Namespace putData(
      @RequestBody Map<String, Object> json, 
      HttpServletRequest request, HttpServletResponse response) throws IOException {
    log.info("You asked for me to PUT: " + json + " to the namespace " + request.getRequestURI());

    return configurationService.storeNamespace(json, getTrimmedPath(request), false);
  }
  
  @PatchMapping(consumes = {"application/json", "application/yaml", "application/yml"},
      value = "/**")
  public @ResponseBody Namespace patchWithData(
      @RequestBody Map<String, Object> json, 
      HttpServletRequest request, HttpServletResponse response) throws IOException {
    log.info("You asked for me to PATCH: " + json + " to the namespace " + request.getRequestURI());

    return configurationService.storeNamespace(json, getTrimmedPath(request), true);
  }

  String getTrimmedPath(HttpServletRequest request) {
    return request.getRequestURI().replace("/configuration", "");
  }
    
}
