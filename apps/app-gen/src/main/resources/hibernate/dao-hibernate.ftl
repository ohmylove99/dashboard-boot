<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
package ${basepackage}.dao.hibernate;

import ${basepackage}.model.${pojo.shortName};
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface ${pojo.shortName}DaoRepository extends 
		PagingAndSortingRepository<${pojo.shortName}, Long>, JpaSpecificationExecutor<${pojo.shortName}> {
}
