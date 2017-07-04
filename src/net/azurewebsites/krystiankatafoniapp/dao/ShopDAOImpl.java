package net.azurewebsites.krystiankatafoniapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import net.azurewebsites.krystiankatafoniapp.model.Shop;
import net.azurewebsites.krystiankatafoniapp.util.ConnectionProvider;
import net.azurewebsites.krystiankatafoniapp.wrapper.ShopOccWrapper;

public class ShopDAOImpl implements ShopDAO {

	private static final String CREATE_SHOP = "INSERT INTO shop(shop_name,user_id) VALUES(:shopname,:userId);";
	private static final String READ_SHOP = "SELECT shop_id, shop_name, user_id FROM category WHERE shop_id=:shop_id;";
	private static final String UPDATE_SHOP = "UPDATE shop SET shop_name=:shopname, user_id=:user_id WHERE shop_id=:shop_id;";
	private static final String DELETE_SHOP = "DELETE FROM shop WHERE shop_id=:shopId ";
	private static final String READ_ALL_SHOPS = "SELECT shop_id, shop_name, user_id FROM shop WHERE user_id=:user_id;";
	private static final String SHOP_IS_USED = "SELECT COUNT(shop_id) FROM purchase WHERE shop_id=:shop_id";
	private static final String AMOUNT_OF_ALL_SHOPS = "SELECT COUNT(shop_id) FROM shop WHERE user_id=:user_id";
	private static final String READ_ALL_SHOPS_FROM_PURCHASES = "SELECT user.user_id, shop.shop_id, shop_name FROM purchase LEFT JOIN shop ON purchase.shop_id=shop.shop_id LEFT JOIN user ON purchase.user_id=user.user_id WHERE purchase.user_id=:user_id;";
	NamedParameterJdbcTemplate template;

	public ShopDAOImpl() {
		template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
	}

	@Override
	public Shop create(Shop shop) {
		Shop resultShop = new Shop(shop);
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(shop);
		int update = template.update(CREATE_SHOP, paramSource, holder);
		if (update > 0) {
			resultShop.setId((Long) holder.getKey());
		}
		return resultShop;
	}

	@Override
	public Shop read(Long primaryKey) {
		Shop resultShop = null;
		SqlParameterSource paramSource = new MapSqlParameterSource("shop_id", primaryKey);
		resultShop = template.queryForObject(READ_SHOP, paramSource, new ShopRowMapper());
		return resultShop;
	}

	@Override
	public boolean update(Shop updateObject) {

		Shop shopCopy = updateObject;
		boolean result = false;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("shop_id", shopCopy.getId());
		paramMap.put("shopname", shopCopy.getShopname());
		paramMap.put("user_id", shopCopy.getUserId());
		SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
		int update = template.update(UPDATE_SHOP, paramSource);
		if (update > 0) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean delete(Long key) {

		boolean result = false;
		SqlParameterSource paramSource = new MapSqlParameterSource("shopId", key);
		if (!shopIsUsed(key)) {
			int update = template.update(DELETE_SHOP, paramSource);
			if (update > 0) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public List<Shop> getAll(Long userId) {
		List<Shop> resultList = null;
		SqlParameterSource paramSource = new MapSqlParameterSource("user_id", userId);
		resultList = template.query(READ_ALL_SHOPS, paramSource, new ShopRowMapper());
		return resultList;
	}

	@Override
	public int amountOfAllShops(Long userId) {
		Integer result = null;
		SqlParameterSource paramSource = new MapSqlParameterSource("user_id", userId);
		Number number = template.queryForObject(AMOUNT_OF_ALL_SHOPS, paramSource, Integer.class);
		if (number == null) {
			result = 0;
		} else {
			result = number.intValue();
		}
		return result;
	}

	public boolean shopIsUsed(long key) {
		boolean result = true;
		SqlParameterSource paramSource = new MapSqlParameterSource("shop_id", key);
		Number number = template.queryForObject(SHOP_IS_USED, paramSource, Integer.class);
		if (number.intValue() > 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public List<Shop> getAllShopsFromPurchases(long userId) {
		List<Shop> resultList = null;
		SqlParameterSource paramSource = new MapSqlParameterSource("user_id", userId);
		resultList = template.query(READ_ALL_SHOPS_FROM_PURCHASES, paramSource, new ShopRowMapper());
		return resultList;
	}
	@Override
	public List<ShopOccWrapper> getWrappedShops(long userId) {
		List<Shop> shopList = new ArrayList<>();
		List<ShopOccWrapper> occList = new ArrayList<>();
		shopList = getAllShopsFromPurchases(userId);
		List<Shop> copyList = new ArrayList<>(shopList);

		shopList.stream().forEach(shopItem -> {

			int freq = Collections.frequency(copyList, shopItem);
			ShopOccWrapper wrapper = new ShopOccWrapper(shopItem, freq);
			if (!occList.contains(wrapper)) {
				occList.add(wrapper);
			}
		});

		return occList;
	}

	private class ShopRowMapper implements RowMapper<Shop> {
		@Override
		public Shop mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Shop shop = new Shop();
			shop.setId(resultSet.getLong("shop_id"));
			shop.setShopname(resultSet.getString("shop_name"));
			shop.setUserId(resultSet.getLong("user_id"));
			return shop;
		}
	}

}
