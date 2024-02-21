package accountbook.infra;

import accountbook.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class CategoryHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Category>> {

    @Override
    public EntityModel<Category> process(EntityModel<Category> model) {
        return model;
    }
}
