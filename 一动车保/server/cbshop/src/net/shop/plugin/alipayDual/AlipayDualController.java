/*
 * 
 * 
 * 
 */
package net.shop.plugin.alipayDual;

import java.math.BigDecimal;

import javax.annotation.Resource;

import net.shop.Message;
import net.shop.controller.admin.BaseController;
import net.shop.entity.PluginConfig;
import net.shop.plugin.PaymentPlugin;
import net.shop.plugin.PaymentPlugin.FeeType;
import net.shop.service.PluginConfigService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 支付宝(双接口)
 * 
 * 
 * 
 */
@Controller("adminAlipayDualController")
@RequestMapping("/admin/payment_plugin/alipay_dual")
public class AlipayDualController extends BaseController {

	@Resource(name = "alipayDualPlugin")
	private AlipayDualPlugin alipayDualPlugin;
	@Resource(name = "pluginConfigServiceImpl")
	private PluginConfigService pluginConfigService;

	/**
	 * 安装
	 */
	@RequestMapping(value = "/install", method = RequestMethod.POST)
	public @ResponseBody
	Message install() {
		if (!alipayDualPlugin.getIsInstalled()) {
			PluginConfig pluginConfig = new PluginConfig();
			pluginConfig.setPluginId(alipayDualPlugin.getId());
			pluginConfig.setIsEnabled(false);
			pluginConfigService.save(pluginConfig);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 卸载
	 */
	@RequestMapping(value = "/uninstall", method = RequestMethod.POST)
	public @ResponseBody
	Message uninstall() {
		if (alipayDualPlugin.getIsInstalled()) {
			PluginConfig pluginConfig = alipayDualPlugin.getPluginConfig();
			pluginConfigService.delete(pluginConfig);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 设置
	 */
	@RequestMapping(value = "/setting", method = RequestMethod.GET)
	public String setting(ModelMap model) {
		PluginConfig pluginConfig = alipayDualPlugin.getPluginConfig();
		model.addAttribute("feeTypes", FeeType.values());
		model.addAttribute("pluginConfig", pluginConfig);
		return "/net/shop/plugin/alipayDual/setting";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(String paymentName, String partner, String key, FeeType feeType, BigDecimal fee, String logo, String description, @RequestParam(defaultValue = "false") Boolean isEnabled, Integer order, RedirectAttributes redirectAttributes) {
		PluginConfig pluginConfig = alipayDualPlugin.getPluginConfig();
		pluginConfig.setAttribute(PaymentPlugin.PAYMENT_NAME_ATTRIBUTE_NAME, paymentName);
		pluginConfig.setAttribute("partner", partner);
		pluginConfig.setAttribute("key", key);
		pluginConfig.setAttribute(PaymentPlugin.FEE_TYPE_ATTRIBUTE_NAME, feeType.toString());
		pluginConfig.setAttribute(PaymentPlugin.FEE_ATTRIBUTE_NAME, fee.toString());
		pluginConfig.setAttribute(PaymentPlugin.LOGO_ATTRIBUTE_NAME, logo);
		pluginConfig.setAttribute(PaymentPlugin.DESCRIPTION_ATTRIBUTE_NAME, description);
		pluginConfig.setIsEnabled(isEnabled);
		pluginConfig.setOrder(order);
		pluginConfigService.update(pluginConfig);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/payment_plugin/list.jhtml";
	}

}