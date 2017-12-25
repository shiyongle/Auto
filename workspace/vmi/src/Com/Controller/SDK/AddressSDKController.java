package Com.Controller.SDK;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Dao.System.IAddressDao;


@Controller
public class AddressSDKController {
	Logger log = LoggerFactory.getLogger(AddressSDKController.class);
	@Resource
	private IAddressDao AddressDao;

	@RequestMapping(value = "/ImpAddressSDK")
	public String ImpAddressSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		reponse.setCharacterEncoding("utf-8");
		try {
			AddressDao.ExecImpAddressSDK(request);
			result = "{success:true,msg:'成功'}";
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString() + "'}";
		}
		
		reponse.getWriter().write(result);

		return null;

	}
}
