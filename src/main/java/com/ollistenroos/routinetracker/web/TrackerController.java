package com.ollistenroos.routinetracker.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ollistenroos.routinetracker.domain.BalanceCalc;
import com.ollistenroos.routinetracker.domain.Entry;
import com.ollistenroos.routinetracker.domain.EntryRepository;
import com.ollistenroos.routinetracker.domain.EntryType;
import com.ollistenroos.routinetracker.domain.EntryTypeRepository;
import com.ollistenroos.routinetracker.domain.Routine;
import com.ollistenroos.routinetracker.domain.RoutineRepository;

@Controller
public class TrackerController {
	
	@Autowired
	private EntryRepository entryRepo;
	
	@Autowired
	private RoutineRepository routineRepo;
	
	@Autowired
	private EntryTypeRepository entryTypeRepo;
	
	private BalanceCalc balanceCalculator = new BalanceCalc();
	
	@RequestMapping(value = {"/", "/routines"})
	public String index(Model model, Principal principal) {
		List<Routine> routines = routineRepo.findByUserName(principal.getName());
		List<Integer> balances = new ArrayList<Integer>();
		int balance = 0;
		for (Routine routine : routines) {
			List<Entry> entries = entryRepo.findByRoutId(routine.getRoutineid());
			if (entries.isEmpty()) {
				balance = 0;
			} else {
				balance = balanceCalculator.balance(entries);
			}
			balances.add(balance);
		}
		model.addAttribute("routines", routines);
		model.addAttribute("balances", balances);
		return "routines";
	}
	
	@RequestMapping(value = "/entries/{routineid}", method = RequestMethod.GET)
	public String entries(@PathVariable("routineid")Long routineId, Model model) {
		List<Entry> entries = entryRepo.findByRoutId(routineId);
		Comparator<Entry> compByDate = Comparator.comparing(e -> e.getDate());
		entries.sort(compByDate.reversed());
		int balance;
		String totalTime;
		if (entries.isEmpty()) {
			balance = 0;
			totalTime = "";
		} else {
			balance = balanceCalculator.balance(entries);
			totalTime = balanceCalculator.totalTime(entries);
		}
		Routine routine = routineRepo.findById(routineId).orElse(null);
		String routineName = routine.getName();
		String displayName = routine.getDisplayName();
		model.addAttribute("entries", entries);
		model.addAttribute("routId", routineId);
		model.addAttribute("balance", balance);
		model.addAttribute("routineName", routineName);
		model.addAttribute("displayName", displayName);
		model.addAttribute("totalTime", totalTime);
		return "entries";
	}
	
	@RequestMapping(value = "/addroutine")
	public String addRoutine(Model model) {
		model.addAttribute("routine", new Routine());
		return "addroutine";
	}
	
	@RequestMapping(value = "/addentry/{routineid}", method = RequestMethod.GET)
	public String addEntry(@PathVariable("routineid") Long routineId, Model model) {
		Routine routine = routineRepo.findById(routineId).orElse(null);
		model.addAttribute("entry", new Entry(routine));
		model.addAttribute("routine", routineRepo.findById(routineId));
		model.addAttribute("entrytypes", entryTypeRepo.findByRoutId(routineId));
		return "addentry";
	}
	
	@RequestMapping(value = "/addentrytype/{routineid}", method = RequestMethod.GET)
	public String addEntryType(@PathVariable("routineid") Long routineId, Model model) {
		Routine routine = routineRepo.findById(routineId).orElse(null);
		model.addAttribute("entryType", new EntryType(routine));
		return "addentrytype";
	}
	
	@RequestMapping(value = "/editroutine/{routineid}", method = RequestMethod.GET)
	public String editRoutine(@PathVariable("routineid") Long routineId, Model model) {
		model.addAttribute("routine", routineRepo.findById(routineId));
		return "editroutine";
	}
	
	@RequestMapping(value = "/editentry/{entryid}", method = RequestMethod.GET)
	public String editEntry(@PathVariable("entryid") Long entryId, Model model) {
		Entry entry = entryRepo.findById(entryId).orElse(null);
		model.addAttribute("entry", entry);
		model.addAttribute("entrytypes", entryTypeRepo.findByRoutId(entry.getRoutId()));
		return "editentry";
	}
	
	@RequestMapping(value = "/editentrytype/{entrytypeid}", method = RequestMethod.GET)
	public String editEntryType(@PathVariable("entrytypeid") Long entrytypeId, Model model) {
		EntryType entryType = entryTypeRepo.findById(entrytypeId).orElse(null);
		List<Entry> allEntries = entryRepo.findByRoutId(entryType.getRoutId());		// Gets all the entries associated with the same routine as entry type.
		List<Entry> entryTypeEntries = new ArrayList<Entry>();						// This List is only for entries that have the entry type we are looking at/editing.
		for (Entry entry : allEntries) {
			if (entry.getEntryType().getEntrytypeid() == entrytypeId) {
				entryTypeEntries.add(entry);
			}
		}
		int totalBalance = balanceCalculator.totalBalance(entryTypeEntries);
		String totalTime = balanceCalculator.totalTime(entryTypeEntries);
		model.addAttribute("entryType", entryType);
		model.addAttribute("entries", entryTypeEntries);
		model.addAttribute("totalBalance", totalBalance);
		model.addAttribute("totalTime", totalTime);
		return "editentrytype";
	}
	
	@RequestMapping(value = "/saveroutine", method = RequestMethod.POST)
	public String saveRoutine(Routine routine, Principal principal) {
		routine.setUserName(principal.getName());
		routineRepo.save(routine);
		return "redirect:routines";
	}
	
	@RequestMapping(value = "/saveentry", method = RequestMethod.POST)
	public String saveEntry(Entry entry) {
		String redirection = "redirect:entries/" + entry.getRoutId().toString();
		entryRepo.save(entry);
		return redirection;
	}
	
	@RequestMapping(value = "/saveentrytype", method = RequestMethod.POST)
	public String saveEntryType(EntryType entryType) {
		String redirection = "redirect:entries/" + entryType.getRoutId().toString();
		entryTypeRepo.save(entryType);
		return redirection;
	}
	
	@RequestMapping(value = "/deleteroutine/{routineid}", method = RequestMethod.GET)
	public String deleteRoutine(@PathVariable("routineid") Long routineId, Model model) {
		routineRepo.deleteById(routineId);
		return "redirect:../routines";
	}
	
	@RequestMapping(value = "/deleteentry/{entryid}", method = RequestMethod.GET)
	public String deleteEntry(@PathVariable("entryid") Long entryId, Model model) {
		Entry entry = entryRepo.findById(entryId).orElse(null);
		String redirection = "redirect:../entries/" + entry.getRoutId().toString();
		entryRepo.deleteById(entryId);
		return redirection;
	}
	
	@RequestMapping(value="/login")
    public String login() {	
        return "login";
    }

}
