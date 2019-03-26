package org.bahmni.feed.openerp.domain.labOrderType;

import org.bahmni.feed.openerp.ObjectMapperRepository;
import org.bahmni.openerp.web.request.builder.Parameter;
import org.ict4h.atomfeed.client.domain.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenMRSRadiologyTestEvent extends OpenMRSLabOrderTypeEvent<OpenMRSRadiologyTest> {
    public static final String RADIOLOGY_TEST_EVENT_NAME = "radiology";
    public static final String SELLABLE = "sellable";

    @Override
    protected List<Parameter> buildParameters(Event event, OpenMRSRadiologyTest openMRSLabOrderTypeEvent) {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("name", openMRSLabOrderTypeEvent.getName()));
        parameters.add(new Parameter("uuid", openMRSLabOrderTypeEvent.getUuid()));
        parameters.add(new Parameter("is_active", getActive(openMRSLabOrderTypeEvent), "boolean"));
        parameters.add(new Parameter("category", "create.radiology.test"));
        return parameters;
    }

    @Override
    protected OpenMRSRadiologyTest readLabOrderTypeEvent(String openMRSLabOrderTypeEventJson) throws IOException {
        return ObjectMapperRepository.objectMapper.readValue(openMRSLabOrderTypeEventJson, OpenMRSRadiologyTest.class);
    }

    private String getActive(OpenMRSRadiologyTest resource) {
        String sellableValueString = resource.getProperties().get(SELLABLE);
        if (sellableValueString != null && !Boolean.valueOf(sellableValueString))
            return "0";
        return resource.getActive();
    }
}
