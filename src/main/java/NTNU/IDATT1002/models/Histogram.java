package NTNU.IDATT1002.models;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Creates histogram table
 */

@Entity
@Table(name = "Histogram")
public class Histogram {

    /**
     * Defines id, may not be blank
     */

    @Id @NotBlank(message = "HistogramId may not be blank")
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

    /**
     * Constrtuctor for Histogram, taking in both histogramId and data
     * @param histogramId
     * @param data
     */


    public Histogram(Long histogramId , String data){
        this.histogramId = histogramId;
        this.data = data;
    }

    /**
     * Constructor taking in a histogram object as parameter
     * @param histogram
     */

    public Histogram(Histogram histogram){
        this(histogram.getHistogramId(),
               histogram.getData());
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
