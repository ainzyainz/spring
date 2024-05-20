package application.controller;

import application.entity.Color;
import application.entity.Material;
import application.service.TableService;
import application.DTO.PageDTO;
import application.DTO.TableDTO;
import com.sun.istack.NotNull;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import static application.utils.Constant.*;

@Controller
public class TableController {

    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping(path = "/tables")
    public String getTables(@NotNull
                            @RequestParam(defaultValue = PAGE_DEFAULT_VALUE) int page,
                            Model model) {
        String field = (String) model.getAttribute(FIELD);
        PageDTO pageDTO = tableService.getTables(field, page);
        model.addAttribute(ADD_TABLE, new TableDTO());
        model.addAttribute(PAGE_LOADER, pageDTO);
        model.addAttribute(PAGE, page);
        model.addAttribute(FIELD, field);
        return INDEX;
    }

    @GetMapping(path = "/read")
    public String readTables(@RequestParam(defaultValue = NULL) String search,
                             @RequestParam(defaultValue = PAGE_DEFAULT_VALUE) int page,
                             Model model) {
        String field = (String) model.getAttribute(FIELD);
        PageDTO pageDTO = tableService.readTables(search, page);
        model.addAttribute(PAGE, page);
        model.addAttribute(SEARCH, search);
        model.addAttribute(PAGE_LOADER, pageDTO);
        model.addAttribute(FIELD, field);

        return INDEX;
    }

    @GetMapping("/displayAdd")
    public String displaySaveTable(Model model, @RequestParam(defaultValue = PAGE_DEFAULT_VALUE) int page) {
        model.addAttribute(ADD_TABLE, new TableDTO());
        model.addAttribute(PAGE, page);
        model.addAttribute(COLORS, Arrays.stream(Color.values()).collect(Collectors.toList()));
        model.addAttribute(MATERIALS, Arrays.stream(Material.values()).collect(Collectors.toList()));
        return ADD;
    }

    @PostMapping(path = "/addTable")
    public String saveTable(@ModelAttribute(ADD_TABLE) @Valid TableDTO tableDTO,
                            BindingResult bindingResult,
                            @RequestParam(defaultValue = PAGE_DEFAULT_VALUE) int page,
                            Model model) {
        if (!bindingResult.hasErrors()) {
            tableService.addTable(tableDTO);
            model.addAttribute(ADD_MESSAGE, CREATE_SUCCESSFUL);
        }
        model.addAttribute(PAGE, page);
        return getTables(page, model);
    }

    @GetMapping("/displayEdit")
    public String displayEditTable(@RequestParam(defaultValue = PAGE_DEFAULT_VALUE) Long id,
                                   @RequestParam(defaultValue = PAGE_DEFAULT_VALUE) int page,
                                   Model model) {
        model.addAttribute(TABLE_TO_UPDATE, tableService.findById(id));
        model.addAttribute(PAGE, page);
        model.addAttribute(COLORS, Arrays.stream(Color.values()).collect(Collectors.toList()));
        model.addAttribute(MATERIALS, Arrays.stream(Material.values()).collect(Collectors.toList()));
        return EDIT;
    }

    @PostMapping(path = "/update")
    public String updateTable(@RequestParam(defaultValue = PAGE_DEFAULT_VALUE) int page,
                              @ModelAttribute(UPDATE_TABLE) @Valid TableDTO tableDTO, BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return EDIT;
        }
        tableService.editTable(tableDTO);
        model.addAttribute(ADD_MESSAGE, UPDATE_SUCCESSFUL);
        return getTables(page, model);
    }

    @PostMapping(path = "/delete")
    public String deleteTable(@RequestParam(defaultValue = PAGE_DEFAULT_VALUE) Long tableId,
                              @RequestParam(defaultValue = PAGE_DEFAULT_VALUE) int page, Model model) {
        tableService.deleteTable(tableId);
        model.addAttribute(ADD_MESSAGE, DELETE_SUCCESSFUL);
        return getTables(page, model);
    }


    @GetMapping(path = "/sort")
    public String sort(@RequestParam(defaultValue = PAGE_DEFAULT_VALUE) int page,
                       @RequestParam String field,
                       @RequestParam String search,
                       Model model) {
        model.addAttribute(FIELD, field);
        model.addAttribute(SEARCH, search);

        if (search == null || search.isEmpty()) {
            return getTables(page, model);
        }
        return readTables(search, page, model);
    }

    @GetMapping(path = "/error")
    private String getError() {
        return ERROR;
    }

}
