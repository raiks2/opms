package ru.vtb.opms.driven.adapter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.opms.driven.dto.ServiceStatusDto;
import ru.vtb.opms.metadata.ArchitecturalElement;

@RestController
@ArchitecturalElement(
        type = ArchitecturalElement.Type.ADAPTER,
        variety = ArchitecturalElement.Variety.REST_ENDPOINT,
        layer = ArchitecturalElement.Layer.INFRASTRUCTURE
)
public class GetServiceStatusEndpoint {

    @GetMapping("/status")
    public ServiceStatusDto invoke() {
        return new ServiceStatusDto(ServiceStatusDto.Status.OK);
    }
}