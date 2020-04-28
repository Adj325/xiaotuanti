import com.adj.xiaotuanti.XiaotuantiApplication;
import com.adj.xiaotuanti.service.TeamService;
import com.adj.xiaotuanti.util.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XiaotuantiApplication.class)
@WebAppConfiguration
public class XiaotuantiApplicationTests {

	@Autowired
	private TeamService teamService;
	@Autowired
	private RedisUtils redisUtils;

	@Test
	public void contextLoads() throws Exception{
		redisUtils.set("oo", "pp");
//		for (Team team: teamService.getTeamsByKeyword("测试介绍默认一")){
//			System.out.println(team.getName());
//		}
	}

}
