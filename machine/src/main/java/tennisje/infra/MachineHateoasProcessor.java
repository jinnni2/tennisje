package tennisje.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import tennisje.domain.*;

@Component
public class MachineHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Machine>> {

    @Override
    public EntityModel<Machine> process(EntityModel<Machine> model) {
        return model;
    }
}
