package cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.security;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.BaseDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.token.AuthToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthTokenDao extends BaseDao<AuthToken> {

    public AuthTokenDao() {
        super(AuthToken.class);
    }

    @Transactional
    public void insertToken(String token, Long userId, LocalDateTime expirationDate) {
        em.createNativeQuery("INSERT INTO auth_tokens (token, user_id, creation_date, expiration_date) VALUES (:token, :userId, :creationDate, :expirationDate)")
                .setParameter("token", token)
                .setParameter("userId", userId)
                .setParameter("creationDate", LocalDateTime.now())
                .setParameter("expirationDate", expirationDate)
                .executeUpdate();
    }

    public Optional<AuthToken> findToken(String token, Integer userId) {
        TypedQuery<AuthToken> query = em.createQuery("SELECT a FROM AuthToken a WHERE a.token = :token AND a.userId = :userId", AuthToken.class);
        query.setParameter("token", token);
        query.setParameter("userId", userId);
        return query.getResultStream().findFirst();
    }

    public List<AuthToken> findActiveUserToken(Long userId) {
        TypedQuery<AuthToken> query = em.createNamedQuery("findActiveUserToken", AuthToken.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
