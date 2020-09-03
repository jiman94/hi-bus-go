package com.younghun.hibusgo.controller;

import static com.younghun.hibusgo.utils.ResponseConstants.RESPONSE_TERMINAL_BAD_REQUEST;

import com.younghun.hibusgo.domain.Route;
import com.younghun.hibusgo.dto.RouteDto;
import com.younghun.hibusgo.service.BusTerminalService;
import com.younghun.hibusgo.service.RouteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {

  private final RouteService routeService;
  private final BusTerminalService busTerminalService;

  /**
   * 노선 조회 메서드
   * @param name 노선 이름
   * @return List<Route>
   */
  @GetMapping("/{name}")
  public ResponseEntity<?> getRoutesByName(@PathVariable String name) {
    List<Route> routes = routeService.searchByName(name);

    return ResponseEntity.ok().body(routes);
  }

  /**
   * 노선 조회 메서드
   * @param routeDto 노선 조회 정보(출발 도착 터미널 아이디, 노선 등급, 출발 시간)
   * @return List<Route>
   */
  @GetMapping()
  public ResponseEntity<?> getRoutesByTerminal(@RequestBody RouteDto routeDto) {
    int departureTerminalId = routeDto.getDepartureTerminalId(); //출발 터미널 아이디
    int arriveTerminalId = routeDto.getArriveTerminalId(); //도착 터미널 아이디

    //출발 터미널 존재 여부
    boolean existDepartureTerminal = busTerminalService.existsById(departureTerminalId);

    if (!existDepartureTerminal) {
      return RESPONSE_TERMINAL_BAD_REQUEST;
    }

    //도착 터미널 존재 여부
    boolean existArriveTerminal = busTerminalService.existsById(arriveTerminalId);

    if (!existArriveTerminal) {
      return RESPONSE_TERMINAL_BAD_REQUEST;
    }

    Route route = routeDto.toEntity();
    List<Route> routes = routeService.searchByTerminal(route);

    return ResponseEntity.ok().body(routes);
  }

}
