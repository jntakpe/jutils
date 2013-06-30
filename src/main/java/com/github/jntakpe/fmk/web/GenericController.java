package com.github.jntakpe.fmk.web;

import com.github.jntakpe.fmk.domain.GenericDomain;
import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.fmk.service.MessageManager;
import com.github.jntakpe.fmk.util.constant.LogLevel;
import com.github.jntakpe.fmk.util.dto.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;

/**
 * Contrôleur abstrait des écrans liste et détail.
 * Les méthodes non 'private' doivent être surchargées si elles ne correspondent pas au fonctionnement souhaité.
 *
 * @author cegiraud
 * @author jntakpe
 */
public abstract class GenericController<T extends GenericDomain> {

    public static final String SUFFIXE_PAGE_HTML_LIST = "_list";

    public static final String SUFFIXE_PAGE_HTML_DETAIL = "_form";

    public static final String PREFIX_CONTROL = "/control";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageManager messageManager;

    /**
     * Méthode permettant de récupérer le service à utiliser.
     *
     * @return interface du service.
     */
    protected abstract GenericService<T> getService();

    /**
     * Méthode permettant de récupérer le nom de la page à afficher.
     *
     * @return le nom de la page
     */
    protected String pageName() {
        return getDomainClass().getSimpleName().toLowerCase();
    }

    /**
     * Affiche la page liste
     *
     * @return le nom de la page à afficher
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display() {
        String screenName = constructNomPageHTMLList();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("{} a affiché l'écran {}", username, screenName);
        return new ModelAndView(screenName);
    }

    /**
     * Renvoi les données de la liste à afficher
     *
     * @return entités à afficher
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<T> listAll() {
        return getService().findAll();
    }

    /**
     * Supprime l'élément possédant l'id de la liste lors d'un appel AJAX
     *
     * @param id identifiant de l'élément à supprimer
     * @return message indiquant le résultat de la suppression
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseMessage delete(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        T entity = getService().findOne(id);
        getService().delete(id);
        messageManager.logMessage("MSG00003", LogLevel.INFO, username, entity);
        return ResponseMessage.getSuccessMessage(messageManager.getMessage("delete.success", entity), entity);
    }

    /**
     * Affiche l'écran détail correspondant à l'élément possédant cette id
     *
     * @param id identifiant de l'élément à afficher
     * @return page détail
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView(constructNomPageHTMLDetail());
        T domain = getService().findOne(id);
        if (domain == null) {
            mv.setViewName(constructNomPageHTMLList());
            return mv.addObject(ResponseMessage.getErrorMessage(messageManager.getMessage("access.notexist")));
        }
        return mv.addObject("domain", domain);
    }

    /**
     * Renvoi une entité en fonction de l'identifiant pour l'affichage de popup
     *
     * @param id identifiant de l'entité
     * @return message indiquant si l'entité a bien été récupérée
     */
    @RequestMapping(value = "/{id}/popup", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage populate(@PathVariable Long id) {
        T entity = getService().findOne(id);
        if (entity == null)
            return ResponseMessage.getErrorMessage(messageManager.getMessage("access.notexist"));
        else
            return ResponseMessage.getSuccessMessage(entity);
    }

    /**
     * Affiche l'écran détail de création d'un nouveau élément
     *
     * @return page détail
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView detail() throws Exception {
        ModelAndView mv = new ModelAndView(constructNomPageHTMLDetail());
        return mv.addObject("domain", getDomainClass().newInstance());
    }

    /**
     * Créé ou modifie l'entité
     *
     * @param domain             entité à sauvegarder
     * @param redirectAttributes attributs de redirection
     *                           permet de faire passer des infos d'une page à l'autre sans session
     * @return page à afficher
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute T domain, RedirectAttributes redirectAttributes) {
        T entity = getService().save(domain);
        String msg = messageManager.getMessage(domain.getId() == null ? "create.success" : "update.success", entity);
        redirectAttributes.addFlashAttribute("responseMessage", msg);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        messageManager.logMessage(domain.getId() == null ? "MSG00001" : "MSG00002", LogLevel.INFO, username, entity);
        return new ModelAndView(getRedirectListView());
    }

    /**
     * Créé ou modifie l'entité
     *
     * @param domain entité à sauvegarder
     * @return message indiquant le résultat de l'opération
     */
    @RequestMapping(value = "/ajax", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage ajaxSave(@ModelAttribute T domain) {
        T entity = getService().save(domain);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String msg = messageManager.getMessage(domain.getId() == null ? "create.success" : "update.success", entity);
        messageManager.logMessage(domain.getId() == null ? "MSG00001" : "MSG00002", LogLevel.INFO, username, entity);
        return ResponseMessage.getSuccessMessage(msg, entity);
    }

    /**
     * Contrôle ajax d'une contrainte d'unicité
     *
     * @param request requête
     * @param id      identifiant du champ
     * @param value   valeur du champ
     * @return true si cette valeur de champ est disponnible
     */
    @RequestMapping(value = "/control*")
    @ResponseBody
    public boolean control(HttpServletRequest request, @RequestParam(required = false) Long id,
                           @RequestParam Object value) {
        String uri = request.getRequestURI();
        String fieldName = uri.substring(uri.lastIndexOf(PREFIX_CONTROL) + PREFIX_CONTROL.length());
        return getService().isAvaillable(fieldName, id, value);
    }

    /**
     * Renvoi la page de la liste depuis un détail
     *
     * @return page liste
     */
    private RedirectView getRedirectListView() {
        String nomPage = pageName();
        if (pageName().charAt(0) != '/')
            nomPage = "/" + nomPage;
        return new RedirectView(nomPage, true);
    }

    /**
     * Méthode renvoyant l'entité de la couche domain/model
     *
     * @return ressource utilisée par le contrôlleur
     */
    private Class<T> getDomainClass() {
        ParameterizedType genericSuperclass = ((ParameterizedType) this.getClass().getGenericSuperclass());
        return (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    /**
     * Construit le nom standard d'une page HTML liste.
     *
     * @return nom de la vue à afficher
     */
    private String constructNomPageHTMLList() {
        return pageName() + SUFFIXE_PAGE_HTML_LIST;
    }

    /**
     * Construit le nom standard d'une page HTML détail.
     *
     * @return nom de la vue à afficher
     */
    private String constructNomPageHTMLDetail() {
        return pageName() + SUFFIXE_PAGE_HTML_DETAIL;
    }

}
