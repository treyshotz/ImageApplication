package NTNU.IDATT1002.repository;

import java.awt.Image;
import java.util.Optional;
import javax.persistence.EntityManager;
import java.util.List;


public class ImageRepository  implements Repository<Image, Long> {

  private EntityManager entityManager;

  public Optional<Image> save (Image image){
    try{
      persist(image);
      return Optional.of(image);
    } catch (Exception e){
      e.printStackTrace();
    }

    return Optional.empty();
  }

  private void persist(Image image){
    entityManager.getTransaction().begin();
    entityManager.persist(image);
    entityManager.getTransaction().commit();
  }

  public Optional<Image> update(Image image){
    return Optional.empty();
  }

  public List<Image> findAll(){
    return entityManager.createQuery("from Image").getResultList();
  }

  public Optional<Image> findById(Long id){
    Image image = entityManager.find(Image.class, id);
    return image != null ? Optional.of(image) : Optional.empty();
  }

  public void delete(Image entity){

  }

  public void deleteById(Long aLong){

  }

  public long count(){
    return 0;
  }

  @Override
  public boolean exists(Image entity) {
    return false;
  }
}
