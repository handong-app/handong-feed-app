package app.handong.feed;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Set;

public class TablePrefix implements PhysicalNamingStrategy {

    private final String tablePrefix = "mydb_";

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return name;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        // 예외 목록 설정
        if (name != null && EXCLUDED_TABLES.contains(name.getText())) {
            return name; // prefix 붙이지 않음
        }
        return applyPrefix(name);
    }
    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return applyPrefix(name);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return name;
    }

    private Identifier applyPrefix(Identifier identifier) {
        if (identifier == null || identifier.getText().isEmpty()) {
            return identifier;
        }
        String newName = tablePrefix + identifier.getText();
        return Identifier.toIdentifier(newName);
    }

    // 예외 테이블 리스트
    private static final Set<String> EXCLUDED_TABLES = Set.of(
            "TbSubject"// prefix 없이 사용하고 싶은 테이블
    );
}
