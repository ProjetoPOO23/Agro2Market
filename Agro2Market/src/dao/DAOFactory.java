package dao;

public class DAOFactory {
    
    public static MarketDAO criarMarketDAO() {
        return new MarketDAOJDBC();
    }
}
