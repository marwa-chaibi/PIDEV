package Services;

import java.sql.SQLException;
import java.util.List;

public interface IServices {

    public interface IService<T> {
        void ajouter(T t) throws SQLException;

        void supprimer(int id);

        void modifier(T t);

        List<T> recuperer();
    }
}
