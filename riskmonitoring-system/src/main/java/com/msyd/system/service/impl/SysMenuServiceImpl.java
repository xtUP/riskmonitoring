package com.msyd.system.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msyd.common.constant.UserConstants;
import com.msyd.common.core.domain.Ztree;
import com.msyd.common.utils.StringUtils;
import com.msyd.system.domain.SysMenu;
import com.msyd.system.domain.SysRole;
import com.msyd.system.domain.SysUser;
import com.msyd.system.mapper.SysMenuMapper;
import com.msyd.system.mapper.SysRoleMenuMapper;
import com.msyd.system.service.ISysMenuService;

/**
 * 菜单 业务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService
{
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    /**
     * 根据用户查询菜单
     * 
     * @param user 用户信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenusByUser(SysUser user)
    {
    	Date date=new Date();
    	SysMenu menu1=new SysMenu();
    	SysMenu menu2=new SysMenu();
    	SysMenu menu3=new SysMenu();
    	SysMenu menu4=new SysMenu();
    	SysMenu menu5=new SysMenu();
    	SysMenu menu6=new SysMenu();
    	SysMenu menu7=new SysMenu();
    	SysMenu menu8=new SysMenu();
    	SysMenu menu9=new SysMenu();
    	SysMenu menu10=new SysMenu();
    	SysMenu menu11=new SysMenu();
    	SysMenu menu12=new SysMenu();
    	menu1.setMenuId(Long.parseLong("118"));
    	menu1.setMenuName("注册绑卡");
    	menu1.setParentId(Long.parseLong("4"));
    	menu1.setOrderNum("3");
    	menu1.setUrl("/echarts/regist_bind");
    	menu1.setTarget("");
    	menu1.setMenuType("C");
    	menu1.setVisible("0");
    	menu1.setPerms("echarts:regist_bind:view");
    	menu1.setIcon("#");
    	menu1.setCreateBy("admin");
    	menu1.setCreateTime(date);
    	menu1.setUpdateBy("xt");
    	menu1.setUpdateTime(date);
    	menu1.setRemark("");
    	
    	menu2.setMenuId(Long.parseLong("119"));
    	menu2.setMenuName("授信");
    	menu2.setParentId(Long.parseLong("4"));
    	menu2.setOrderNum("4");
    	menu2.setUrl("/echarts/credit_apply");
    	menu2.setTarget("");
    	menu2.setMenuType("C");
    	menu2.setVisible("0");
    	menu2.setPerms("echarts:credit_apply:view");
    	menu2.setIcon("#");
    	menu2.setCreateBy("admin");
    	menu2.setCreateTime(date);
    	menu2.setUpdateBy("xt");
    	menu2.setUpdateTime(date);
    	menu2.setRemark("");
    	menu3.setMenuId(Long.parseLong("120"));
    	menu3.setMenuName("提款");
    	menu3.setParentId(Long.parseLong("4"));
    	menu3.setOrderNum("5");
    	menu3.setUrl("/echarts/loan_apply");
    	menu3.setTarget("");
    	menu3.setMenuType("C");
    	menu3.setVisible("0");
    	menu3.setPerms("echarts:loan_apply:view");
    	menu3.setIcon("#");
    	menu3.setCreateBy("admin");
    	menu3.setCreateTime(date);
    	menu3.setUpdateBy("xt");
    	menu3.setUpdateTime(date);
    	menu3.setRemark("");
    	menu4.setMenuId(Long.parseLong("121"));
    	menu4.setMenuName("放款");
    	menu4.setParentId(Long.parseLong("4"));
    	menu4.setOrderNum("6");
    	menu4.setUrl("/echarts/loan_release");
    	menu4.setTarget("");
    	menu4.setMenuType("C");
    	menu4.setVisible("0");
    	menu4.setPerms("echarts:loan_release:view");
    	menu4.setIcon("#");
    	menu4.setCreateBy("admin");
    	menu4.setCreateTime(date);
    	menu4.setUpdateBy("xt");
    	menu4.setUpdateTime(date);
    	menu4.setRemark("");
    	//
    	menu6.setMenuId(Long.parseLong("122"));
    	menu6.setMenuName("指标-1");
    	menu6.setParentId(Long.parseLong("4"));
    	menu6.setOrderNum("7");
    	menu6.setUrl("/echarts/node_part1");
    	menu6.setTarget("");
    	menu6.setMenuType("C");
    	menu6.setVisible("0");
    	menu6.setPerms("echarts:node_part1:view");
    	menu6.setIcon("#");
    	menu6.setCreateBy("admin");
    	menu6.setCreateTime(date);
    	menu6.setUpdateBy("xt");
    	menu6.setUpdateTime(date);
    	menu6.setRemark("");
    	//
    	menu7.setMenuId(Long.parseLong("123"));
    	menu7.setMenuName("指标-2");
    	menu7.setParentId(Long.parseLong("4"));
    	menu7.setOrderNum("8");
    	menu7.setUrl("/echarts/node_part2");
    	menu7.setTarget("");
    	menu7.setMenuType("C");
    	menu7.setVisible("0");
    	menu7.setPerms("echarts:node_part2:view");
    	menu7.setIcon("#");
    	menu7.setCreateBy("admin");
    	menu7.setCreateTime(date);
    	menu7.setUpdateBy("xt");
    	menu7.setUpdateTime(date);
    	menu7.setRemark("");
    	//
    	menu8.setMenuId(Long.parseLong("124"));
    	menu8.setMenuName("指标-3");
    	menu8.setParentId(Long.parseLong("4"));
    	menu8.setOrderNum("9");
    	menu8.setUrl("/echarts/node_part3");
    	menu8.setTarget("");
    	menu8.setMenuType("C");
    	menu8.setVisible("0");
    	menu8.setPerms("echarts:node_part3:view");
    	menu8.setIcon("#");
    	menu8.setCreateBy("admin");
    	menu8.setCreateTime(date);
    	menu8.setUpdateBy("xt");
    	menu8.setUpdateTime(date);
    	menu8.setRemark("");
    	//
    	menu9.setMenuId(Long.parseLong("125"));
    	menu9.setMenuName("指标-4");
    	menu9.setParentId(Long.parseLong("4"));
    	menu9.setOrderNum("10");
    	menu9.setUrl("/echarts/node_part4");
    	menu9.setTarget("");
    	menu9.setMenuType("C");
    	menu9.setVisible("0");
    	menu9.setPerms("echarts:node_part4:view");
    	menu9.setIcon("#");
    	menu9.setCreateBy("admin");
    	menu9.setCreateTime(date);
    	menu9.setUpdateBy("xt");
    	menu9.setUpdateTime(date);
    	menu9.setRemark("");
    	//
    	menu10.setMenuId(Long.parseLong("126"));
    	menu10.setMenuName("指标-5");
    	menu10.setParentId(Long.parseLong("4"));
    	menu10.setOrderNum("11");
    	menu10.setUrl("/echarts/node_part5");
    	menu10.setTarget("");
    	menu10.setMenuType("C");
    	menu10.setVisible("0");
    	menu10.setPerms("echarts:node_part5:view");
    	menu10.setIcon("#");
    	menu10.setCreateBy("admin");
    	menu10.setCreateTime(date);
    	menu10.setUpdateBy("xt");
    	menu10.setUpdateTime(date);
    	menu10.setRemark("");
    	//
    	menu5.setMenuId(Long.parseLong("127"));
    	menu5.setMenuName("节点线图1");
    	menu5.setParentId(Long.parseLong("4"));
    	menu5.setOrderNum("12");
    	menu5.setUrl("/echarts/node_line1_echarts");
    	menu5.setTarget("");
    	menu5.setMenuType("C");
    	menu5.setVisible("0");
    	menu5.setPerms("echarts:line_echarts:view");
    	menu5.setIcon("#");
    	menu5.setCreateBy("admin");
    	menu5.setCreateTime(date);
    	menu5.setUpdateBy("xt");
    	menu5.setUpdateTime(date);
    	menu5.setRemark("");
    	//
    	menu11.setMenuId(Long.parseLong("128"));
    	menu11.setMenuName("节点线图2");
    	menu11.setParentId(Long.parseLong("4"));
    	menu11.setOrderNum("13");
    	menu11.setUrl("/echarts/node_line2_echarts");
    	menu11.setTarget("");
    	menu11.setMenuType("C");
    	menu11.setVisible("0");
    	menu11.setPerms("echarts:line_echarts:view");
    	menu11.setIcon("#");
    	menu11.setCreateBy("admin");
    	menu11.setCreateTime(date);
    	menu11.setUpdateBy("xt");
    	menu11.setUpdateTime(date);
    	menu11.setRemark("");
    	//树图
    	menu12.setMenuId(Long.parseLong("129"));
    	menu12.setMenuName("授信环节");
    	menu12.setParentId(Long.parseLong("4"));
    	menu12.setOrderNum("14");
    	menu12.setUrl("/echarts/Credit_tree");
    	menu12.setTarget("");
    	menu12.setMenuType("C");
    	menu12.setVisible("0");
    	menu12.setPerms("echarts:tree:view");
    	menu12.setIcon("#");
    	menu12.setCreateBy("admin");
    	menu12.setCreateTime(date);
    	menu12.setUpdateBy("xt");
    	menu12.setUpdateTime(date);
    	menu12.setRemark("");
    	List<SysMenu> menus = new LinkedList<SysMenu>();
        // 管理员显示所有菜单信息
        if (user.isAdmin())
        {
            menus = menuMapper.selectMenuNormalAll();
            menus.add(menu1);
            menus.add(menu2);
            menus.add(menu3);
            menus.add(menu4);
            menus.add(menu6);
            menus.add(menu7);
            menus.add(menu8);
            menus.add(menu9);
            menus.add(menu10);
            menus.add(menu5);
            menus.add(menu11);
            menus.add(menu12);
        }
        else
        {
            menus = menuMapper.selectMenusByUserId(user.getUserId());
        }
        return getChildPerms(menus, 0);
    }

    /**
     * 查询菜单集合
     * 
     * @return 所有菜单信息
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId)
    {
        List<SysMenu> menuList = null;
        if (SysUser.isAdmin(userId))
        {
            menuList = menuMapper.selectMenuList(menu);
        }
        else
        {
            menu.getParams().put("userId", userId);
            menuList = menuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }

    /**
     * 查询菜单集合
     * 
     * @return 所有菜单信息
     */
    @Override
    public List<SysMenu> selectMenuAll(Long userId)
    {
        List<SysMenu> menuList = null;
        if (SysUser.isAdmin(userId))
        {
            menuList = menuMapper.selectMenuAll();
        }
        else
        {
            menuList = menuMapper.selectMenuAllByUserId(userId);
        }
        return menuList;
    }

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectPermsByUserId(Long userId)
    {
        List<String> perms = menuMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据角色ID查询菜单
     * 
     * @param role 角色对象
     * @return 菜单列表
     */
    @Override
    public List<Ztree> roleMenuTreeData(SysRole role, Long userId)
    {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<SysMenu> menuList = selectMenuAll(userId);
        if (StringUtils.isNotNull(roleId))
        {
            List<String> roleMenuList = menuMapper.selectMenuTree(roleId);
            ztrees = initZtree(menuList, roleMenuList, true);
        }
        else
        {
            ztrees = initZtree(menuList, null, true);
        }
        return ztrees;
    }

    /**
     * 查询所有菜单
     * 
     * @return 菜单列表
     */
    @Override
    public List<Ztree> menuTreeData(Long userId)
    {
        List<SysMenu> menuList = selectMenuAll(userId);
        List<Ztree> ztrees = initZtree(menuList);
        return ztrees;
    }

    /**
     * 查询系统所有权限
     * 
     * @return 权限列表
     */
    @Override
    public LinkedHashMap<String, String> selectPermsAll(Long userId)
    {
        LinkedHashMap<String, String> section = new LinkedHashMap<>();
        List<SysMenu> permissions = selectMenuAll(userId);
        if (StringUtils.isNotEmpty(permissions))
        {
            for (SysMenu menu : permissions)
            {
                section.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING, menu.getPerms()));
            }
        }
        return section;
    }

    /**
     * 对象转菜单树
     * 
     * @param menuList 菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysMenu> menuList)
    {
        return initZtree(menuList, null, false);
    }

    /**
     * 对象转菜单树
     * 
     * @param menuList 菜单列表
     * @param roleMenuList 角色已存在菜单列表
     * @param permsFlag 是否需要显示权限标识
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysMenu> menuList, List<String> roleMenuList, boolean permsFlag)
    {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleMenuList);
        for (SysMenu menu : menuList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(menu.getMenuId());
            ztree.setpId(menu.getParentId());
            ztree.setName(transMenuName(menu, permsFlag));
            ztree.setTitle(menu.getMenuName());
            if (isCheck)
            {
                ztree.setChecked(roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }

    public String transMenuName(SysMenu menu, boolean permsFlag)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(menu.getMenuName());
        if (permsFlag)
        {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
        }
        return sb.toString();
    }

    /**
     * 删除菜单管理信息
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(Long menuId)
    {
        return menuMapper.deleteMenuById(menuId);
    }

    /**
     * 根据菜单ID查询信息
     * 
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenu selectMenuById(Long menuId)
    {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 查询子菜单数量
     * 
     * @param parentId 父级菜单ID
     * @return 结果
     */
    @Override
    public int selectCountMenuByParentId(Long parentId)
    {
        return menuMapper.selectCountMenuByParentId(parentId);
    }

    /**
     * 查询菜单使用数量
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int selectCountRoleMenuByMenuId(Long menuId)
    {
        return roleMenuMapper.selectCountRoleMenuByMenuId(menuId);
    }

    /**
     * 新增保存菜单信息
     * 
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(SysMenu menu)
    {
        return menuMapper.insertMenu(menu);
    }

    /**
     * 修改保存菜单信息
     * 
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenu menu)
    {
        return menuMapper.updateMenu(menu);
    }

    /**
     * 校验菜单名称是否唯一
     * 
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public String checkMenuNameUnique(SysMenu menu)
    {
        Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
        SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (StringUtils.isNotNull(info) && info.getMenuId().longValue() != menuId.longValue())
        {
            return UserConstants.MENU_NAME_NOT_UNIQUE;
        }
        return UserConstants.MENU_NAME_UNIQUE;
    }

    /**
     * 根据父节点的ID获取所有子节点
     * 
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId)
    {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();)
        {
            SysMenu t = (SysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId)
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     * 
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t)
    {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext())
                {
                    SysMenu n = (SysMenu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t)
    {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext())
        {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
