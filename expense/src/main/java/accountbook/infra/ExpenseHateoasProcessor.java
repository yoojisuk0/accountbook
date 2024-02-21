package accountbook.infra;

import accountbook.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class ExpenseHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Expense>> {

    @Override
    public EntityModel<Expense> process(EntityModel<Expense> model) {
        return model;
    }
}
