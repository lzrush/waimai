package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import lombok.Setter;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {
    Integer countByMap(Map map);

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into dish (name, category_id, price, image, description, status, create_time, update_time, create_user, update_user) "
    +"values" + "(#{name},#{categoryId},#{price},#{image},#{description},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insert(Dish dish);

    Page<DishVO> page(DishPageQueryDTO dishPageQueryDTO);

    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    void deleteByIds(List<Long> ids);

    DishVO findById(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    @AutoFill(OperationType.UPDATE)
    @Update("update dish set status = #{status},update_time = #{updateTime},update_user=#{updateUser} where id = #{id}")
    void updateStatus(Dish dish);

    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> list(Dish dish);

    @Select("select * from dish d ,setmeal_dish sd  where d.id = sd.dish_id and sd.setmeal_id = #{setmealId} ")
    List<Dish> findBySetmealId(Long setmealId);

}
