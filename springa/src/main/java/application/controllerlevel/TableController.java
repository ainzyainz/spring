package application.controllerlevel;

import application.entity.Table;
import application.servicelevel.TableService;
import application.utils.DTO.PageDTO;
import application.utils.DTO.TableDTO;
import application.utils.enums.Color;
import application.utils.enums.Material;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static application.utils.constant.ConstantContainer.*;

@Controller
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping(path = "/tables")
    public String getTables(@NotNull
                            @RequestParam(defaultValue = PAGE_DEFAULT_VALUE) int page,
                            Model model) {
        PageDTO pageDTO = tableService.buildPageDTO(tableService.getTables(), page);
        model.addAttribute(ADD_TABLE, new TableDTO());
        model.addAttribute(PAGE_LOADER, pageDTO);
        model.addAttribute(PAGE, page);
        return INDEX;
    }

    @GetMapping(path = "/read")
    public String readTables(@RequestParam(defaultValue = "null") String search, @RequestParam(defaultValue = "0") int page, Model model) {
        PageDTO pageDTO = tableService.buildPageDTO(tableService.readTables(search, page), page);
        model.addAttribute(PAGE, page);
        model.addAttribute(PAGE_LOADER, pageDTO);
        model.addAttribute(SEARCH, search);
        model.addAttribute(LIST_TABLE, pageDTO.getList());
        return SEARCH;
    }
    @GetMapping("/displayAdd")
    public String displaySaveTable(Model model,@RequestParam(defaultValue = "0") int page){
        model.addAttribute(ADD_TABLE, new TableDTO());
        model.addAttribute(PAGE,page);
        model.addAttribute("colors", Arrays.stream(Color.values()).collect(Collectors.toList()));
        model.addAttribute("materials",Arrays.stream(Material.values()).collect(Collectors.toList()));
        return "add";
    }
    @PostMapping(path = "/addTable")
    public String saveTable(@ModelAttribute("addTable") TableDTO tableDTO, @RequestParam(defaultValue = "0") int page,
                            Model model) {
        model.addAttribute(ADD_MESSAGE, CREATE_SUCCESSFUL);
        model.addAttribute(ADD_TABLE,new TableDTO());
        model.addAttribute(PAGE,page);
        return tableService.addTable(tableDTO) != null ? getTables(page,model) : getError();
    }
    @GetMapping("/displayEdit")
    public String displayEditTable(@RequestParam long id,
                                   Model model, @RequestParam(defaultValue = "0") int page){
        model.addAttribute("tableToUpdate", tableService.findById(id));
        //TODO придумать как передать целую ДТО
        model.addAttribute(PAGE,page);
        model.addAttribute("colors", Arrays.stream(Color.values()).collect(Collectors.toList()));
        model.addAttribute("materials",Arrays.stream(Material.values()).collect(Collectors.toList()));
        return "edit";
    }

    @PostMapping(path = "/update")
    public String updateTable(@RequestParam(defaultValue = "0") int page,
                              @ModelAttribute("updateTable") TableDTO tableDTO, Model model) {
        tableService.editTable(tableDTO);
        model.addAttribute(ADD_MESSAGE, UPDATE_SUCCESSFUL);
        return getTables(page, model);
    }

    @PostMapping(path = "/delete")
    public String deleteTable(@RequestParam(defaultValue = "0") Long tableId,
                              @RequestParam(defaultValue = "0") int page, Model model) {
        tableService.deleteTable(tableId);
        model.addAttribute(ADD_MESSAGE, DELETE_SUCCESSFUL);
        return getTables(page, model);
    }

    @GetMapping(path = "/error")
    private String getError() {
        return ERROR;
    }

}
