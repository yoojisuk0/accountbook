package accountbook.infra;

import accountbook.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class IncomeHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Income>> {

    @Override
    public EntityModel<Income> process(EntityModel<Income> model) {
        return model;
    }
}
