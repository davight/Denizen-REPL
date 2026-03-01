package objects;

import com.denizenscript.denizencore.objects.Fetchable;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.VectorObject;
import com.denizenscript.denizencore.tags.ObjectTagProcessor;
import com.denizenscript.denizencore.tags.TagContext;

public class VectorTag implements VectorObject {

    private double x, y, z;

    private String prefix;

    @Fetchable("vec")
    public static VectorTag valueOf(String string, TagContext context) {
        if (string == null) {
            return null;
        }
        if (string.startsWith("vec@")) {
            string = string.substring("vec@".length());
        }
        String[] split = string.split(",");
        return new VectorTag(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
    }

    public static boolean matches(String arg) {
        return arg.startsWith("vec@") || valueOf(arg, null) != null;
    }

    public VectorTag(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public boolean isUnique() {
        return false;
    }

    @Override
    public String identify() {
        return "vec@" + x + "," + y + "," + z;
    }

    @Override
    public String identifySimple() {
        return identify();
    }

    @Override
    public VectorObject duplicate() {
        return null;
    }

    @Override
    public ObjectTag setPrefix(String s) {
        prefix = s;
        return this;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setZ(double z) {
        this.z = z;
    }

    public static ObjectTagProcessor<VectorTag> tagProcessor = new ObjectTagProcessor<>();
}
