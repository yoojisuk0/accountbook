package accountbook.infra;

import accountbook.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class CashHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Cash>> {

    @Override
    public EntityModel<Cash> process(EntityModel<Cash> model) {
        return model;
    }
}
