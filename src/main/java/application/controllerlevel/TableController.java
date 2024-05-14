package application.controllerlevel;

import application.entity.Table;
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
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping(path = "/tables")
    public String getTables(@NotNull @RequestParam(defaultValue = "0") int page,
                            Model model) {
        model.addAttribute("addTable", new TableDTO());
        model.addAttribute("page", page);
        model.addAttribute("listOfTables", tableService.getTables(page, 5));
        return "index";
    }

    @GetMapping(path = "/mainPage")
    public @ResponseBody
    List<TableDTO> getTable() {
        return tableService.readAll();
    }

    @GetMapping(path = "/read/{search}")
    public String readTables(@PathVariable String search,
                             @RequestParam(defaultValue = "0")int page, Model model) {
        model.addAttribute("addTable",new TableDTO());
        model.addAttribute("page",page);
        model.addAttribute("listOfTables",tableService.readTables(search,page));
        return "index";
    }

    @PostMapping(path = "/addTable")
    public String  saveTable(@ModelAttribute("addTable") TableDTO tableDTO, @RequestParam int page, Model model) {
     model.addAttribute("addMessage", "create new table successful");
        return tableService.addTable(tableDTO) != null ? getTables(page, model) : getError();
    }

    @PostMapping(path = "/update/{id}")
    public ResponseEntity<TableDTO> updateTable(@PathVariable Long id, @RequestBody TableDTO tableDTO) {
        return tableService.editTable(id, tableDTO) != null ? new ResponseEntity<>(tableDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/delete")
    public String deleteTable(@RequestParam Long id, @RequestParam int page, Model model) {
        tableService.deleteTable(id);
        model.addAttribute("addMessage", "delete table successful");
        return  getTables(page, model);
    }
    //TODO изменить на пост

    @GetMapping(path = "/error")
    private String getError() {
        return "error";
    }

}
