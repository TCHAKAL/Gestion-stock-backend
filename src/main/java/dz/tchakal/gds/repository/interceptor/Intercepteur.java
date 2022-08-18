package dz.tchakal.gds.repository.interceptor;

import org.hibernate.EmptyInterceptor;

public class Intercepteur extends EmptyInterceptor {

    @Override
    public String onPrepareStatement(String sql) {
//        if (StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")) {
//            String entityName = sql.substring(7, sql.indexOf("."));//exemple select utilisateu0_.
//            String entreprise = MDC.get("entreprise");
//            if (StringUtils.hasLength(entityName)
//                    && !sql.toLowerCase().contains("from entreprise ")
//                    && !sql.toLowerCase().contains("from role ")
//                    && StringUtils.hasLength(entityName)) {
//                if (sql.toLowerCase().contains("where")) {
//                    sql = sql + " and " + entityName + ".entreprise = " + entreprise;
//                } else {
//                    sql = sql + " where " + entityName + ".entreprise = " + entreprise;
//                }
//            }
//        }
        return super.onPrepareStatement(sql);
    }
}
