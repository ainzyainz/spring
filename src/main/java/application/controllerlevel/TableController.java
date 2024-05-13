package application.controllerlevel;

import application.servicelevel.TableService;
import application.utils.DTO.PaginationDto;
import application.utils.DTO.TableDTO;
import com.sun.istack.NotNull;
import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping(path = "/start")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping(path = "/tables")
    public String getTables(@NotNull @RequestParam(defaultValue = "0") int page,
                            Model model) {
        model.addAttribute("addTable", new PaginationDto());
        model.addAttribute("page", page);
        tableService.getTables(page, 5);
        model.addAttribute("listOfTables", tableService.getTables(page, 5));
        return "index";
    }

    @GetMapping(path = "/mainPage")
    public @ResponseBody
    List<TableDTO> getTable() {
        return tableService.readAll();
    }

    @GetMapping(path = "/read/{search}")
    public List<TableDTO> readTables(@PathVariable String search) {
        return tableService.readTables(search);
    }

    @PostMapping(path = "/addTable")
    public void saveTable(@NotNull @RequestParam int page,
                            @ModelAttribute("addTable") TableDTO tableDTO, PaginationDto paginationDto) {
        tableService.addTable(tableDTO) != null ? getTables() : "errors";

        //TODO обработать фейлы в save
    }

    @PostMapping(path = "/update/{id}")
    public ResponseEntity<TableDTO> updateTable(@PathVariable Long id, @RequestBody TableDTO tableDTO) {
        return tableService.editTable(id, tableDTO) != null ? new ResponseEntity<>(tableDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "delete/{id}")
    public ResponseEntity<HttpStatus> deleteTable(@PathVariable Long id) {
        tableService.deleteTable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
