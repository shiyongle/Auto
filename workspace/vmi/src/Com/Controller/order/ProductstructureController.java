package Com.Controller.order;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import Com.Dao.order.IProductstructureDao;

@Controller
public class ProductstructureController {
	@Resource
	IProductstructureDao ProductstructureDao;
}
