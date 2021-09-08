package com.trackandtrail.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.trackandtrail.common.dto.PaginationDTO;

public class GenericSearchandPaginationUtil<T> {

	public Page<T> searchBySpecificColumns(PaginationDTO pagination, Map<String, String> columnNames,
			Class<T> className, EntityManager entityManager) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> queryBase = builder.createQuery(className);
		Root<T> root = queryBase.from(className);
		List<Predicate> queryConditions = new ArrayList<>();
		List<Predicate> orQueryConditions = new ArrayList<>();
		List<String> searchTokens = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(pagination.getSearchData(), ":");
		while (st.hasMoreTokens())
			searchTokens.add(st.nextToken());
		if (searchTokens.isEmpty())
			searchTokens.add("");

		Field[] fields = className.getDeclaredFields();
		for (Field field : fields) {
			if (!columnNames.keySet().parallelStream().filter(o -> o.equalsIgnoreCase(field.getName())).findFirst()
					.isPresent()) {
				columnNames.put(field.getName(), "Field");
			}
		}

		for (String token : searchTokens) {
			columnNames.entrySet().forEach(o -> {
				if (o.getValue().contains("Field")) {
					if (token.contains("#") && token.split("#")[0].equalsIgnoreCase(o.getKey())) {
						Predicate search = null;
						String key = token.split("#")[0].trim();
						String value = token.split("#")[1].trim();
						if (value.contains("!"))
							search = builder.notLike(builder.upper(root.get(key).as(String.class)),
									"%" + value.toUpperCase().replace("!", "") + "%");
						else if (value.contains(",")) {
							List<String> filters = new ArrayList<>(Arrays.asList(value.split(",")));
							filters.parallelStream().forEach(f -> {
								Predicate search1 = builder.like(builder.upper(root.get(key).as(String.class)),
										"%" + f.trim().toUpperCase() + "%");
								orQueryConditions.add(search1);
							});
						} else
							search = builder.like(builder.upper(root.get(key).as(String.class)),
									"%" + value.toUpperCase() + "%");
						if (null != search)
							queryConditions.add(search);
					} else if (!token.contains("#")) {
						Predicate orSearch = null;
						if (token.contains("!"))
							orSearch = builder.notLike(builder.upper(root.get(o.getKey()).as(String.class)),
									"%" + token.trim().toUpperCase().replace("!", "") + "%");
						else if (token.contains(",")) {
							List<String> filters = new ArrayList<>(Arrays.asList(token.split(",")));
							filters.parallelStream().forEach(f -> {
								Predicate search1 = builder.like(builder.upper(root.get(o.getKey()).as(String.class)),
										"%" + f.trim().toUpperCase() + "%");
								orQueryConditions.add(search1);
							});
						} else
							orSearch = builder.like(builder.upper(root.get(o.getKey()).as(String.class)),
									"%" + token.trim().toUpperCase() + "%");
						if (null != orSearch)
							orQueryConditions.add(orSearch);
					}

				} else {
					Join<T, T> joinRoot = root.join(o.getKey(), JoinType.LEFT);
					if (token.contains("#") && token.split("#")[0].equalsIgnoreCase(o.getKey())) {
						Predicate predicate = null;
						String value = token.split("#")[1].trim();
						if (token.split("#")[1].contains("!"))
							predicate = builder.notLike(builder.upper(joinRoot.get(o.getValue()).as(String.class)),
									"%" + value.toUpperCase().replace("!", "") + "%");
						else if (token.contains(",")) {
							List<String> filters = new ArrayList<>(Arrays.asList(token.split(",")));
							filters.parallelStream().forEach(f -> {
								Predicate search1 = builder.like(builder.upper(root.get(o.getKey()).as(String.class)),
										"%" + f.trim().toUpperCase() + "%");
								orQueryConditions.add(search1);
							});
						} else
							predicate = builder.like(builder.upper(joinRoot.get(o.getValue()).as(String.class)),
									"%" + value.toUpperCase() + "%");
						if (null != predicate)
							queryConditions.add(predicate);
					} else if (!token.contains("#")) {
						Predicate orPredicate = builder.like(builder.upper(joinRoot.get(o.getValue()).as(String.class)),
								"%" + token.trim().toUpperCase() + "%");
						if (null != orPredicate)
							queryConditions.add(orPredicate);
					}

				}
			});
		}
		if (pagination.getSortBy() != null && !pagination.getSortBy().isEmpty()) {
			if (pagination.getSortOrder() != null && !pagination.getSortOrder().isEmpty()) {
				if (pagination.getSortOrder().toUpperCase().startsWith("A"))
					queryBase.orderBy(builder.asc(root.get(pagination.getSortBy())));
				else
					queryBase.orderBy(builder.desc(root.get(pagination.getSortBy())));
			} else
				queryBase.orderBy(builder.asc(root.get(pagination.getSortBy())));
		} else
			queryBase.orderBy(builder.asc(root.get(columnNames.entrySet().iterator().next().getKey())));

		List<T> queryResult = null;
		if (queryConditions.size() == 0)
			queryResult = entityManager
					.createQuery(queryBase.where(builder.or(orQueryConditions.toArray(new Predicate[] {}))))
					.setFirstResult(pagination.getPageNo() * pagination.getPageSize())
					.setMaxResults(pagination.getPageSize()).getResultList();

		if (orQueryConditions.size() == 0)
			queryResult = entityManager
					.createQuery(queryBase.where(builder.and(queryConditions.toArray(new Predicate[] {}))))
					.setFirstResult(pagination.getPageNo() * pagination.getPageSize())
					.setMaxResults(pagination.getPageSize()).getResultList();

		if (queryConditions.size() != 0 && orQueryConditions.size() != 0)
			queryResult = entityManager
					.createQuery(queryBase.where(builder.or(orQueryConditions.toArray(new Predicate[] {})),
							builder.and(queryConditions.toArray(new Predicate[] {}))))
					.setFirstResult(pagination.getPageNo() * pagination.getPageSize())
					.setMaxResults(pagination.getPageSize()).getResultList();

		// count query
		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
		Root<T> countRoot = countQuery.from(className);

		Set<Join<T, ?>> joins = root.getJoins();
		for (Join<T, ?> join : joins) {
			countRoot.join(join.getAttribute().getName());
		}

		if (queryConditions.size() == 0)
			countQuery.select(builder.count(countRoot))
					.where(builder.or(orQueryConditions.toArray(new Predicate[] {})));

		if (orQueryConditions.size() == 0)
			countQuery.select(builder.count(countRoot)).where(builder.and(queryConditions.toArray(new Predicate[] {})));

		if (queryConditions.size() != 0 && orQueryConditions.size() != 0)
			countQuery.select(builder.count(countRoot)).where(builder.and(queryConditions.toArray(new Predicate[] {})),
					builder.or(orQueryConditions.toArray(new Predicate[] {})));

		Long count = entityManager.createQuery(countQuery).getSingleResult();

		Pageable pageable = PageRequest.of(pagination.getPageNo(), pagination.getPageSize());

		Page<T> result = new PageImpl<>(queryResult, pageable, count);

		return result;
	}

	public Page<T> searchByColumns(PaginationDTO pagination, Map<String, String> columnNames, Class<T> className,
			EntityManager entityManager) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> queryBase = builder.createQuery(className);
		Root<T> root = queryBase.from(className);
		List<Predicate> queryConditions = new ArrayList<>();
		List<String> searchTokens = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(pagination.getSearchData(), ":");
		while (st.hasMoreTokens())
			searchTokens.add(st.nextToken());
		if (searchTokens.isEmpty())
			searchTokens.add("");

		Field[] fields = className.getDeclaredFields();
		for (Field field : fields) {
			if (!columnNames.keySet().parallelStream().filter(o -> o.equalsIgnoreCase(field.getName())).findFirst()
					.isPresent()) {
				columnNames.put(field.getName(), "Field");
			}
		}

		for (String token : searchTokens) {
			columnNames.entrySet().forEach(o -> {
				if (o.getValue().contains("Field")) {
					Predicate search = builder.like(builder.upper(root.get(o.getKey()).as(String.class)),
							"%" + token.toUpperCase() + "%");
					queryConditions.add(search);
				} else {
					Join<T, T> joinRoot = root.join(o.getKey(), JoinType.LEFT);
					Predicate predicate = builder.like(builder.upper(joinRoot.get(o.getValue()).as(String.class)),
							"%" + token.toUpperCase() + "%");
					queryConditions.add(predicate);
				}
			});
		}
		if (pagination.getSortBy() != null && !pagination.getSortBy().isEmpty()) {
			if (pagination.getSortOrder() != null && !pagination.getSortOrder().isEmpty()) {
				if (pagination.getSortOrder().toUpperCase().startsWith("A"))
					queryBase.orderBy(builder.asc(root.get(pagination.getSortBy())));
				else
					queryBase.orderBy(builder.desc(root.get(pagination.getSortBy())));
			} else
				queryBase.orderBy(builder.asc(root.get(pagination.getSortBy())));
		} else
			queryBase.orderBy(builder.asc(root.get(columnNames.entrySet().iterator().next().getKey())));

		List<T> queryResult = entityManager
				.createQuery(queryBase.where(builder.or(queryConditions.toArray(new Predicate[] {}))))
				.setFirstResult(pagination.getPageNo() * pagination.getPageSize())
				.setMaxResults(pagination.getPageSize()).getResultList();

		// count query
		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
		Root<T> countRoot = countQuery.from(className);
		// new

		columnNames.entrySet().forEach(o -> {
			if (o.getValue().contains("Field")) {
				countQuery.select(builder.count(countRoot))
						.where(builder.or(queryConditions.toArray(new Predicate[] {})));
			} else {
				Join<T, T> countryCount = countRoot.join(o.getKey(), JoinType.LEFT);
				List<Predicate> countPredicate = new ArrayList<>();
				countPredicate.add(queryConditions.get(0));
				countPredicate.add(builder.like(builder.upper(countryCount.get(o.getValue())),
						"%" + pagination.getSearchData().toUpperCase() + "%"));
				countQuery.select(builder.count(countRoot))
						.where(builder.or(countPredicate.toArray(new Predicate[] {})));
			}
		});

		// new

		Long count = entityManager.createQuery(countQuery).getSingleResult();

		Pageable pageable = PageRequest.of(pagination.getPageNo(), pagination.getPageSize());

		Page<T> result = new PageImpl<>(queryResult, pageable, count);

		return result;
	}
}
