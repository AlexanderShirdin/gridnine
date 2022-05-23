package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        List<Flight> fly = FlightBuilder.createFlights();

        List<Flight> fly2 = new ArrayList<>(fly);
        List<Flight> dFlights = new ArrayList<>();

        for (int i = 0; i < fly.size(); i++) {
            Flight flight = fly.get(i);
            List<Segment> segments = flight.getSegments();
            for (int j = 0; j < segments.size(); j++) {
                if (segments.size() >= 2) {
                    boolean equal = segments.get(1).getDepartureDate().minusHours(2).isAfter(segments.get(0).getArrivalDate()) |
                            segments.get(segments.size() - 1).getDepartureDate().minusHours(2).isAfter(segments.get(1).getArrivalDate());
                    if (equal) {
                        dFlights.add(flight);
                    }
                }
            }
        }
        System.out.println("All flights:");
        fly.forEach(System.out::println);
        System.out.println();
        System.out.println("1) Without flights, with departure up to the current time:");
        fly.stream()
                .filter(e -> e.getSegments().stream().anyMatch(p -> p.getDepartureDate().isAfter(LocalDateTime.now())))
                .forEach(System.out::println);
        System.out.println();

        System.out.println("2) Without flights, with an arrival date earlier than the departure date:");
        fly.stream()
                .filter(e -> e.getSegments().stream().anyMatch(p -> p.getDepartureDate().isBefore(p.getArrivalDate())))
                .forEach(System.out::println);
        System.out.println();

        System.out.println("3) Without flights, the total time spent on the ground of which exceeds 2 hours:");
        List<Flight> duplicates = fly2.stream().filter(dFlights::contains).toList();
        fly2.removeAll(duplicates);
        for (Flight flight : fly2) {
            System.out.println(flight);
        }
    }
}