package ai.elimu.rest.v2.applications;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.entity.application.Application;
import ai.elimu.entity.application.ApplicationVersion;
import ai.elimu.model.v2.gson.application.ApplicationGson;
import ai.elimu.model.v2.gson.application.ApplicationVersionGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/applications", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class ApplicationsRestController {

  private final ApplicationDao applicationDao;

  private final ApplicationVersionDao applicationVersionDao;

  @GetMapping
  public String handleGetRequest(HttpServletRequest request) {
    log.info("handleGetRequest");

    JSONArray applicationsJsonArray = new JSONArray();
    for (Application application : applicationDao.readAll()) {
      ApplicationGson applicationGson = JpaToGsonConverter.getApplicationGson(application);

      List<ApplicationVersionGson> applicationVersionGsons = new ArrayList<>();
      for (ApplicationVersion applicationVersion : applicationVersionDao.readAll(application)) {
        ApplicationVersionGson applicationVersionGson = JpaToGsonConverter.getApplicationVersionGson(applicationVersion);
        applicationVersionGsons.add(applicationVersionGson);
      }
      applicationGson.setApplicationVersions(applicationVersionGsons);

      String json = new Gson().toJson(applicationGson);
      applicationsJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = applicationsJsonArray.toString();
    log.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
