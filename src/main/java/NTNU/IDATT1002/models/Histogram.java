package NTNU.IDATT1002.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Creates histogram table
 */

@Entity
@Table(name = "histogram")
public class Histogram {

    /**
     * Defines id, may not be blank
     */
    @Id
    @GeneratedValue
    private Long histogramId;

    /**
     * One to one relations, joins histogramId
     * On columns histogramId in metadata
     */
    @OneToOne
    @JoinColumn(name = "metadata_id")
    private Metadata metadata;

    @NotBlank (message = "Data may not be blank")
    private String data;

    public Histogram() {
    }

    /**
     * Constrtuctor for Histogram, taking in both histogramId and data
     *
     * @param data
     */
    public Histogram(String data){
        this.data = data;
    }

    public Long getHistogramId() {
        return histogramId;
    }

    public String getData() {
        return data;
    }

    public void setHistogramId(Long histogramId) {
        this.histogramId = histogramId;
    }

    public void setData(String data) {
        this.data = data;
    }

}
