package mchorse.chameleon.lib.data.model;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class ModelCube
{
    public List<ModelQuad> quads = new ArrayList<ModelQuad>();
    public Vector3f origin = new Vector3f();
    public Vector3f size = new Vector3f();
    public Vector3f pivot = new Vector3f();
    public Vector3f rotation = new Vector3f();
    public float inflate;

    /* Texture mapping */
    public ModelUV north;
    public ModelUV east;
    public ModelUV south;
    public ModelUV west;
    public ModelUV up;
    public ModelUV down;

    public void setupBoxUV(Vector2f boxUV, boolean mirror)
    {
        /* North */
        float tMinX = boxUV.x + this.size.z;
        float tMinY = boxUV.y + this.size.z;
        float tMaxX = tMinX + this.size.x;
        float tMaxY = tMinY + this.size.y;

        if (mirror)
        {
            float tmp = tMaxX;

            tMaxX = tMinX;
            tMinX = tmp;
        }

        this.north = ModelUV.from(tMinX, tMinY, tMaxX, tMaxY);

        /* East */
        tMinX = boxUV.x;
        tMinY = boxUV.y + this.size.z;
        tMaxX = tMinX + this.size.z;
        tMaxY = tMinY + this.size.y;

        if (mirror)
        {
            tMinX = boxUV.x + this.size.z + this.size.x;
            tMinY = boxUV.y + this.size.z;
            tMaxX = tMinX + this.size.z;
            tMaxY = tMinY + this.size.y;

            float tmp = tMinX;

            tMinX = tMaxX;
            tMaxX = tmp;
        }

        this.east = ModelUV.from(tMinX, tMinY, tMaxX, tMaxY);

        /* South */
        tMinX = boxUV.x + this.size.z * 2 + this.size.x;
        tMinY = boxUV.y + this.size.z;
        tMaxX = tMinX + this.size.x;
        tMaxY = tMinY + this.size.y;

        if (mirror)
        {
            float tmp = tMaxX;

            tMaxX = tMinX;
            tMinX = tmp;
        }

        this.south = ModelUV.from(tMinX, tMinY, tMaxX, tMaxY);

        /* West */
        tMinX = boxUV.x + this.size.z + this.size.x;
        tMinY = boxUV.y + this.size.z;
        tMaxX = tMinX + this.size.z;
        tMaxY = tMinY + this.size.y;

        if (mirror)
        {
            tMinX = boxUV.x;
            tMinY = boxUV.y + this.size.z;
            tMaxX = tMinX + this.size.z;
            tMaxY = tMinY + this.size.y;

            float tmp = tMinX;

            tMinX = tMaxX;
            tMaxX = tmp;
        }

        this.west = ModelUV.from(tMinX, tMinY, tMaxX, tMaxY);

        /* Up */
        tMinX = boxUV.x + this.size.z;
        tMinY = boxUV.y;
        tMaxX = tMinX + this.size.x;
        tMaxY = tMinY + this.size.z;

        if (mirror)
        {
            float tmp = tMaxX;

            tMaxX = tMinX;
            tMinX = tmp;
        }

        this.up = ModelUV.from(tMinX, tMinY, tMaxX, tMaxY);

        /* Down */
        tMinX = boxUV.x + this.size.z + this.size.x;
        tMinY = boxUV.y + this.size.z;
        tMaxX = tMinX + this.size.x;
        tMaxY = boxUV.y;

        if (mirror)
        {
            float tmp = tMaxX;

            tMaxX = tMinX;
            tMinX = tmp;
        }

        this.down = ModelUV.from(tMinX, tMinY, tMaxX, tMaxY);
    }

    public void generateQuads(Model model)
    {
        ModelUV uv;
        float tw = 1F / model.textureWidth;
        float th = 1F / model.textureHeight;

        float minX = (this.origin.x - this.size.x - this.inflate) / 16F;
        float minY = (this.origin.y - this.inflate) / 16F;
        float minZ = (this.origin.z - this.inflate) / 16F;

        float maxX = (this.origin.x + this.inflate) / 16F;
        float maxY = (this.origin.y + this.size.y + this.inflate) / 16F;
        float maxZ = (this.origin.z + this.size.z + this.inflate) / 16F;

        if (this.north != null)
        {
            uv = this.north;

            this.quads.add(new ModelQuad()
                .vertex(maxX, minY, minZ, uv.sx() * tw, uv.ey() * th)
                .vertex(minX, minY, minZ, uv.ex() * tw, uv.ey() * th)
                .vertex(minX, maxY, minZ, uv.ex() * tw, uv.sy() * th)
                .vertex(maxX, maxY, minZ, uv.sx() * tw, uv.sy() * th)
                .normal(0, 0, -1));
        }

        if (this.east != null)
        {
            uv = this.east;

            this.quads.add(new ModelQuad()
                .vertex(maxX, minY, maxZ, uv.sx() * tw, uv.ey() * th)
                .vertex(maxX, minY, minZ, uv.ex() * tw, uv.ey() * th)
                .vertex(maxX, maxY, minZ, uv.ex() * tw, uv.sy() * th)
                .vertex(maxX, maxY, maxZ, uv.sx() * tw, uv.sy() * th)
                .normal(1, 0, 0));
        }

        if (this.south != null)
        {
            uv = this.south;

            this.quads.add(new ModelQuad()
                .vertex(minX, minY, maxZ, uv.sx() * tw, uv.ey() * th)
                .vertex(maxX, minY, maxZ, uv.ex() * tw, uv.ey() * th)
                .vertex(maxX, maxY, maxZ, uv.ex() * tw, uv.sy() * th)
                .vertex(minX, maxY, maxZ, uv.sx() * tw, uv.sy() * th)
                .normal(0, 0, 1));
        }

        if (this.west != null)
        {
            uv = this.west;

            this.quads.add(new ModelQuad()
                .vertex(minX, minY, minZ, uv.sx() * tw, uv.ey() * th)
                .vertex(minX, minY, maxZ, uv.ex() * tw, uv.ey() * th)
                .vertex(minX, maxY, maxZ, uv.ex() * tw, uv.sy() * th)
                .vertex(minX, maxY, minZ, uv.sx() * tw, uv.sy() * th)
                .normal(-1, 0, 0));
        }

        if (this.up != null)
        {
            uv = this.up;

            this.quads.add(new ModelQuad()
                .vertex(maxX, maxY, minZ, uv.sx() * tw, uv.ey() * th)
                .vertex(minX, maxY, minZ, uv.ex() * tw, uv.ey() * th)
                .vertex(minX, maxY, maxZ, uv.ex() * tw, uv.sy() * th)
                .vertex(maxX, maxY, maxZ, uv.sx() * tw, uv.sy() * th)
                .normal(0, 1, 0));
        }

        if (this.down != null)
        {
            uv = this.down;

            this.quads.add(new ModelQuad()
                .vertex(minX, minY, minZ, uv.ex() * tw, uv.sy() * th)
                .vertex(maxX, minY, minZ, uv.sx() * tw, uv.sy() * th)
                .vertex(maxX, minY, maxZ, uv.sx() * tw, uv.ey() * th)
                .vertex(minX, minY, maxZ, uv.ex() * tw, uv.ey() * th)
                .normal(0, -1, 0));
        }
    }
}