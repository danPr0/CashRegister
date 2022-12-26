package util.captcha;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleCaptchaService {
    public static Captcha createCaptcha() {
        List<Color> colors = List.of(Color.BLACK, Color.RED, Color.BLUE, Color.GREEN);

        List<Font> fonts = new ArrayList<>();
        List<String> fontNames = List.of("Arial", "Serif", "Georgia", "Sans-serif", "Helvetica", "Comic Sans Serif");
        fontNames.forEach(name -> fonts.add(new Font(name, Font.ITALIC, 40)));

        return new Captcha.Builder(120, 40)
                .addText(new DefaultWordRenderer(colors, fonts))
                .addBackground(new GradiatedBackgroundProducer(Color.white, Color.white))
                .gimp()
                .addBorder()
                .build();
    }
}
