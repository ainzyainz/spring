package application.controllerlevel;

import application.servicelevel.TableService;
import application.utils.DTO.TableDTO;
import com.sun.istack.NotNull;
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
    public String getTables(@NotNull @RequestParam int page,
                             @NotNull @RequestParam int size,
                            Model model) {

        tableService.getTables(page, size);
        model.addAttribute("listOfTables", tableService.getTables(page, size));
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

    @PostMapping(path = "/create")
    public ResponseEntity<TableDTO> saveTable(@RequestBody TableDTO tableDTO) {
        tableService.addTable(tableDTO);
        return new ResponseEntity<>(tableDTO, HttpStatus.OK);
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
