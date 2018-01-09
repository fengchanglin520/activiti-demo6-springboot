package com.yawn;

import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiDemo6SpringbootApplicationTests {

    @Resource
    private ProcessEngineConfiguration configuration;
    @Resource
    private ProcessEngine engine;

	@Test
	public void contextLoads() {
        configuration.setDatabaseSchemaUpdate("drop-create");
        configuration.buildProcessEngine();
	}

	@Test
    public void test() {
//        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        IdentityService is = engine.getIdentityService();
        // 添加用户组
        Group empGroup = saveGroup(is, "empGroup", "员工组");
        Group manageGroup = saveGroup(is, "manageGroup", "经理组");
        Group dirGroup = saveGroup(is, "dirGroup", "总监组");
        // 添加用户
        User empA = saveUser(is, "empa"); // 员工a
        User empB = saveUser(is, "empb"); // 员工b
        User managea = saveUser(is, "managea"); // 经理a
        User manageb = saveUser(is, "manageb"); // 经理b
        User dira = saveUser(is, "dira"); // 总监a
        // 绑定关系
        saveRel(is, empA, empGroup);
        saveRel(is, empB, empGroup);
        saveRel(is, managea, manageGroup);
        saveRel(is, manageb, manageGroup);
        saveRel(is, dira, dirGroup);
    }

    User saveUser(IdentityService is, String id) {
        User u = is.newUser(id);
        u.setPassword("123456");
        is.saveUser(u);
        return u;
    }

    Group saveGroup(IdentityService is, String id, String name) {
        Group g = is.newGroup(id);
        g.setName(name);
        is.saveGroup(g);
        return g;
    }

    void saveRel(IdentityService is, User u, Group g) {
        is.createMembership(u.getId(), g.getId());
    }


    @Test
    public void test2() {
//	    / 无用 ： spring自动部署流程文件
//        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        Deployment dep = rs.createDeployment().addClasspathResource("classpath:/processes/vacation.bpmn").deploy();
        ProcessDefinition pd = rs.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();
        rs.addCandidateStarterGroup(pd.getId(), "empGroup");
    }

}
