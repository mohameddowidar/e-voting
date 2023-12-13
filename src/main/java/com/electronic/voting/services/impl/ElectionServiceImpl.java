package com.electronic.voting.services.impl;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Candidate;
import com.electronic.voting.entities.Election;
import com.electronic.voting.repositories.ElectionRepository;
import com.electronic.voting.repositories.VoteRepository;
import com.electronic.voting.services.ElectionService;
import com.electronic.voting.services.VoteService;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ElectionServiceImpl implements ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public List<Election> findAll() {
        return electionRepository.findAll(Sort.by("updatedDate").descending());
    }

    @Override
    public List<Election> findAllWithNumbers() {
        List<Election> all = electionRepository.findAll();
        all.stream().forEach(item->{
            item.setPieModel(createPieModel(item.getCandidates(), item.getElectionId()));
        });
        return all;
    }

    private PieChartModel createPieModel(Set<Candidate> candidateSet, Integer electionId) {
        PieChartModel pieModel = new PieChartModel();
        ChartData data = new ChartData();
        Random random = new Random();

        // Calculate the total votes for all candidates to determine percentages
        Number totalVotes = candidateSet.stream()
                .mapToLong(candidate -> voteRepository.getCountByElectionId(electionId, candidate.getCandidateId()))
                .sum();

        candidateSet.stream().forEach(item -> {
            Number count = voteRepository.getCountByElectionId(electionId, item.getCandidateId());
            item.setTotalVotes(count);
            String randomColor = String.format("#%06x", random.nextInt(0xffffff + 1));
            item.setColor(randomColor);
        });

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        List<String> bgColors = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        candidateSet.forEach(candidate -> {
            Number count = candidate.getTotalVotes();
            values.add(count);
            bgColors.add(candidate.getColor());
            // Calculate percentage of total votes for each candidate
            double percentage = (totalVotes.doubleValue() > 0) ? count.doubleValue() / totalVotes.doubleValue() * 100 : 0;
            labels.add(candidate.getVoter().getName() + " (" + String.format("%.2f", percentage) + "%)");
        });

        dataSet.setData(values);
        dataSet.setBackgroundColor(bgColors);
        data.addChartDataSet(dataSet);
        data.setLabels(labels);

        pieModel.setData(data);
        return pieModel;
    }

    @Override
    public List<Election> findCurrentElections() {
        return electionRepository.findAllWithDateBetween(new Date());
    }

    @Override
    public Election findById(Integer id) {
        return electionRepository.findById(id).orElse(null);
    }

    @Override
    public Election save(Election election) {
        return electionRepository.save(election);
    }

    @Override
    public void delete(Integer id) {
        electionRepository.deleteElectionByElectionId(id);
    }

    @Override
    public Page<Election> searchVElection(SearchDTO searchDTO, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate").descending());
        String searchString = (searchDTO.getTitle() == null) ? null : "%" + searchDTO.getTitle() + "%";
        return electionRepository.findAll(searchString, pageable);
    }

    @Override
    public int getAllElectionCount() {
        return (int) electionRepository.count();
    }

    @Override
    public boolean isThereActiveInitiativePeroidsBetweenDates(Integer periodId, Date startDate, Date endDate) {
        int size = electionRepository.findActiveInitiativePeroids(periodId, startDate, endDate);
        if(size> 0 ) {
            return true;
        }
        return false;
    }

}

