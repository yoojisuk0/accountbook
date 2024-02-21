package accountbook.infra;

import accountbook.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class AccountHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Account>> {

    @Override
    public EntityModel<Account> process(EntityModel<Account> model) {
        return model;
    }
}
