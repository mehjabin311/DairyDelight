package com.dairyproject.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import com.dairyproject.entities.PurchaseDetails;

import jakarta.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface PurchaseRecordRepository extends JpaRepository<PurchaseDetails, Integer> {

	@Query("select p from PurchaseDetails p")
	public List<PurchaseDetails> findAllPurchaseDetails();

//    @Query("select p from PurchaseDetails p JOIN FETCH p.sellerDetails where p.consumerDetails.emailId = :emailId")
//    public List<PurchaseDetails> findAllPurchaseDetailsByConsumerEmailId(@Param("emailId") String emailId);

	@QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "false"))
	@Query("SELECT p FROM PurchaseDetails p " + "JOIN FETCH p.consumerDetails " + "JOIN FETCH p.sellerDetails "
			+ "JOIN FETCH p.productDetails " + "WHERE p.consumerDetails.emailId = :emailId")
	public List<PurchaseDetails> findAllPurchaseDetailsByConsumerEmailId(@Param("emailId") String emailId);

	@Query("select p from PurchaseDetails p where p.sellerDetails.emailId = :emailId")
	public List<PurchaseDetails> findAllPurchaseDetailsBySellerEmailId(@Param("emailId") String emailId);

	@Query("select p from PurchaseDetails p where p.purchaseId = :purchaseId")
	public PurchaseDetails findPurchaseDetailsByPurchaseId(@Param("purchaseId") Integer purchaseId);
}

//@Repository
//public interface PurchaseRecordRepository extends CrudRepository<PurchaseDetails, Integer> {
//
//	@Query("select p from PurchaseDetails p")
//	public List<PurchaseDetails> findAllPurchaseDetails();
//
//	@Query("select p from PurchaseDetails p where consumerDetails.emailId = ?1")
//	public List<PurchaseDetails> findAllPurchaseDetailsByConsumerEmailId(String emailId);
//
//	@Query("select p from PurchaseDetails p where sellerDetails.emailId = ?1")
//	public List<PurchaseDetails> findAllPurchaseDetailsBySellerEmailId(String emailId);
//
//	@Query("select p from PurchaseDetails p where p.purchaseId = ?1")
//	public PurchaseDetails findPurchaseDetailsByPurchaseId(Integer purchaseId);
//
//}
