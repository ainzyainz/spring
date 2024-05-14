package application.controllerlevel;

import application.servicelevel.TableService;
import application.utils.DTO.PageDTO;
import application.utils.DTO.TableDTO;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        PageDTO pageDTO = tableService.buildPageDTO(tableService.getTables(page, 5), page);
        System.out.println(pageDTO.getTotalPages());
        model.addAttribute("addTable", new TableDTO());
        model.addAttribute("pageLoader", pageDTO);
        model.addAttribute("page", page);
        return "index";
    }

    @GetMapping(path = "/mainPage")
    public @ResponseBody
    List<TableDTO> getTable() {
        return tableService.readAll();
    }

    @GetMapping(path = "/read")
    public String readTables(@RequestParam String search, @RequestParam(defaultValue = "0") int page, Model model) {
        PageDTO pageDTO = tableService.buildPageDTO(tableService.readTables(search,page),page);
        model.addAttribute("page", page);
        model.addAttribute("pageLoader", pageDTO);
        model.addAttribute("search", search);
        System.out.println(pageDTO.getTotalPages());
        model.addAttribute("listTables", tableService.readTables(search, page));
        return "search";
    }

    @PostMapping(path = "/addTable")
    public String saveTable(@ModelAttribute("addTable") TableDTO tableDTO, @RequestParam int page, Model model) {
        model.addAttribute("addMessage", "create new table successful");
        return tableService.addTable(tableDTO) != null ? getTables(page, model) : getError();
    }

    @PostMapping(path = "/update/{id}")
    public ResponseEntity<TableDTO> updateTable(@PathVariable Long id, @RequestBody TableDTO tableDTO) {
        return tableService.editTable(id, tableDTO) != null ? new ResponseEntity<>(tableDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/delete")
    public String deleteTable(@RequestParam Long tableId, @RequestParam int page, Model model) {
        tableService.deleteTable(tableId);
        model.addAttribute("addMessage", "delete table successful");
        return getTables(page, model);
    }

    @GetMapping(path = "/error")
    private String getError() {
        return "error";
    }

}
