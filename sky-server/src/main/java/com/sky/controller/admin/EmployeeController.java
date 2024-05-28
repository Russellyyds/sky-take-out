package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = {"employee相关接口"})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);
        System.out.println(employeeLoginDTO);
        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "员工登出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    @ApiOperation(value = "新增员工")
    @PostMapping("")
    public Result<String> save(@RequestBody EmployeeDTO employee) {
        System.out.println("当前线程id：" + Thread.currentThread().getId());

        log.info("新增员工：", employee);
        employeeService.save(employee);
        return Result.success();
    }

    @ApiOperation(value = "分页查询 员工信息")
    @GetMapping("/page")
    public Result<Object> pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("Query参数:"+employeePageQueryDTO);
       PageResult pageResult= employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }
    @ApiOperation(value = "员工账号启用还是禁用")
    @PostMapping("/status/{status}")
    public Result editStatus(@PathVariable("status") Integer status,@RequestParam Long id){
        log.info("启动禁用员工账号");
        employeeService.startOrStop(status,id);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "查看当前员工信息")
    public Result<Employee> getById(@PathVariable("id") Long id){
        Employee employee=employeeService.queryEmployeeById(id);
        return Result.success(employee);
    }
    @PutMapping
    @ApiOperation(value = "编辑员工信息")
    public Result<Employee> updateEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("updateEmployee information");
        employeeService.updateEmployeeInformation(employeeDTO);
        return Result.success();
    }
}
