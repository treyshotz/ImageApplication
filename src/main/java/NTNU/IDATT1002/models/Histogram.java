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
    @OneToOne(mappedBy = "histogram")
    private Metadata metadata;

    @Lob
    @NotBlank(message = "Data may not be blank")
    private String data;

    public Histogram() {
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

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
