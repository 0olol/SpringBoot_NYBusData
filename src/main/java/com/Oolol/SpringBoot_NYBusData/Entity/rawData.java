package com.Oolol.SpringBoot_NYBusData.Entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rawData")
public class rawData {

        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "recordedAtTime")
    private LocalDateTime recordedAtTime;

    @Column(name = "directionRef")
    private Integer directionRef;

    @Column(name = "publishedLineName")
    private String publishedLineName;

    @Column(name = "vehicleRef")
    private String vehicleRef;

    // @Column(name = "correct")
    // private String correct;
    
    @Override
    public String toString() {
      return "Tutorial [id=" + ", title=" + recordedAtTime + ", desc=" + directionRef + ", published=" + publishedLineName + "vehicleRef=" + vehicleRef + "]";
    }


    public rawData(LocalDateTime recordedAtTime,Integer directionRef,String publishedLineName,String vehicleRef){
        this.recordedAtTime = recordedAtTime;
        this.directionRef = directionRef;
        this.publishedLineName = publishedLineName;
        this.vehicleRef = vehicleRef;
        // this.correct = "false";
    }

  // Doing extra custom query
    // public void update4(LocalDateTime recordedAtTime,String vehicleRef ){
    //   this.recordedAtTime = recordedAtTime;
    //   this.vehicleRef = vehicleRef;
    // }


}
