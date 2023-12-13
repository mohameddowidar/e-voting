package com.electronic.voting.managedBeans;

import com.electronic.voting.entities.Election;
import com.electronic.voting.services.ElectionService;
import lombok.Data;
import lombok.extern.java.Log;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("view")
@Data
@Log
public class ResultsMB implements Serializable {
    private  List<Election> elections;

    @Autowired
    private ElectionService electionService;


    @PostConstruct
    public void init() {
        this.elections = electionService.findAllWithNumbers();
    }
}