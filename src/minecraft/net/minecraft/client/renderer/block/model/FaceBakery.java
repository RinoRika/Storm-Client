package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.*;
import net.minecraftforge.client.model.*;
import net.minecraft.src.*;
import shadersmod.client.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.util.vector.*;
import org.lwjgl.util.vector.Matrix4f;

import net.minecraft.util.*;

public class FaceBakery
{
	private static final float field_178418_a;
    private static final float field_178417_b;
    private static final String __OBFID = "CL_00002490";
    
    public BakedQuad makeBakedQuad(final Vector3f posFrom, final Vector3f posTo, final BlockPartFace face, final TextureAtlasSprite sprite, final EnumFacing facing, final ModelRotation modelRotationIn, final BlockPartRotation partRotation, final boolean uvLocked, final boolean shade) {
        return this.makeBakedQuad(posFrom, posTo, face, sprite, facing, (ITransformation)modelRotationIn, partRotation, uvLocked, shade);
    }
    
    public BakedQuad makeBakedQuad(final Vector3f posFrom, final Vector3f posTo, final BlockPartFace face, final TextureAtlasSprite sprite, final EnumFacing facing, final ITransformation modelRotationIn, final BlockPartRotation partRotation, final boolean uvLocked, final boolean shade) {
        final int[] var10 = this.makeQuadVertexData(face, sprite, facing, this.getPositionsDiv16(posFrom, posTo), modelRotationIn, partRotation, uvLocked, shade);
        final EnumFacing var11 = getFacingFromVertexData(var10);
        if (uvLocked) {
            this.func_178409_a(var10, var11, face.blockFaceUV, sprite);
        }
        if (partRotation == null) {
            this.applyFacing(var10, var11);
        }
        if (Reflector.ForgeHooksClient_fillNormal.exists()) {
            Reflector.callVoid(Reflector.ForgeHooksClient_fillNormal, var10, var11);
        }
        return new BakedQuad(var10, face.tintIndex, var11, sprite);
    }
    
    private int[] makeQuadVertexData(final BlockPartFace p_178405_1_, final TextureAtlasSprite p_178405_2_, final EnumFacing p_178405_3_, final float[] p_178405_4_, final ITransformation p_178405_5_, final BlockPartRotation p_178405_6_, final boolean p_178405_7_, final boolean shade) {
        int vertexSize = 28;
        if (Config.isShaders()) {
            vertexSize = 56;
        }
        final int[] aint = new int[vertexSize];
        for (int var10 = 0; var10 < 4; ++var10) {
            this.fillVertexData(aint, var10, p_178405_3_, p_178405_1_, p_178405_4_, p_178405_2_, p_178405_5_, p_178405_6_, p_178405_7_, shade);
        }
        return aint;
    }
    
    private int getFaceShadeColor(final EnumFacing p_178413_1_) {
        final float var2 = this.getFaceBrightness(p_178413_1_);
        final int var3 = MathHelper.clamp_int((int)(var2 * 255.0f), 0, 255);
        return 0xFF000000 | var3 << 16 | var3 << 8 | var3;
    }
    
    private float getFaceBrightness(final EnumFacing p_178412_1_) {
        switch (SwitchEnumFacing.field_178400_a[p_178412_1_.ordinal()]) {
            case 1: {
                if (Config.isShaders()) {
                    return Shaders.blockLightLevel05;
                }
                return 0.5f;
            }
            case 2: {
                return 1.0f;
            }
            case 3:
            case 4: {
                if (Config.isShaders()) {
                    return Shaders.blockLightLevel08;
                }
                return 0.8f;
            }
            case 5:
            case 6: {
                if (Config.isShaders()) {
                    return Shaders.blockLightLevel06;
                }
                return 0.6f;
            }
            default: {
                return 1.0f;
            }
        }
    }
    
    private float[] getPositionsDiv16(final Vector3f pos1, final Vector3f pos2) {
        final float[] afloat = new float[EnumFacing.values().length];
        afloat[EnumFaceDirection.Constants.WEST_INDEX] = pos1.x / 16.0f;
        afloat[EnumFaceDirection.Constants.DOWN_INDEX] = pos1.y / 16.0f;
        afloat[EnumFaceDirection.Constants.NORTH_INDEX] = pos1.z / 16.0f;
        afloat[EnumFaceDirection.Constants.EAST_INDEX] = pos2.x / 16.0f;
        afloat[EnumFaceDirection.Constants.UP_INDEX] = pos2.y / 16.0f;
        afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = pos2.z / 16.0f;
        return afloat;
    }
    
    private void fillVertexData(final int[] faceData, final int vertexIndex, final EnumFacing facing, final BlockPartFace partFace, final float[] p_178402_5_, final TextureAtlasSprite sprite, final ITransformation modelRotationIn, final BlockPartRotation partRotation, final boolean uvLocked, final boolean shade) {
        final EnumFacing var11 = modelRotationIn.rotate(facing);
        final int var12 = shade ? this.getFaceShadeColor(var11) : -1;
        final EnumFaceDirection.VertexInformation var13 = EnumFaceDirection.getFacing(facing).func_179025_a(vertexIndex);
        final Vector3f var14 = new Vector3f(p_178402_5_[var13.field_179184_a], p_178402_5_[var13.field_179182_b], p_178402_5_[var13.field_179183_c]);
        this.func_178407_a(var14, partRotation);
        final int var15 = this.rotateVertex(var14, facing, vertexIndex, modelRotationIn, uvLocked);
        this.storeVertexData(faceData, var15, vertexIndex, var14, var12, sprite, partFace.blockFaceUV);
    }
    
    private void storeVertexData(final int[] faceData, final int storeIndex, final int vertexIndex, final Vector3f position, final int shadeColor, final TextureAtlasSprite sprite, final BlockFaceUV faceUV) {
        final int step = faceData.length / 4;
        final int i = storeIndex * step;
        faceData[i] = Float.floatToRawIntBits(position.x);
        faceData[i + 1] = Float.floatToRawIntBits(position.y);
        faceData[i + 2] = Float.floatToRawIntBits(position.z);
        faceData[i + 3] = shadeColor;
        faceData[i + 4] = Float.floatToRawIntBits(sprite.getInterpolatedU(faceUV.func_178348_a(vertexIndex)));
        faceData[i + 4 + 1] = Float.floatToRawIntBits(sprite.getInterpolatedV(faceUV.func_178346_b(vertexIndex)));
    }
    
    private void func_178407_a(final Vector3f p_178407_1_, final BlockPartRotation p_178407_2_) {
        if (p_178407_2_ != null) {
            final Matrix4f var3 = this.getMatrixIdentity();
            final Vector3f var4 = new Vector3f(0.0f, 0.0f, 0.0f);
            switch (SwitchEnumFacing.field_178399_b[p_178407_2_.axis.ordinal()]) {
                case 1: {
                    Matrix4f.rotate(p_178407_2_.angle * 0.017453292f, new Vector3f(1.0f, 0.0f, 0.0f), var3, var3);
                    var4.set(0.0f, 1.0f, 1.0f);
                    break;
                }
                case 2: {
                    Matrix4f.rotate(p_178407_2_.angle * 0.017453292f, new Vector3f(0.0f, 1.0f, 0.0f), var3, var3);
                    var4.set(1.0f, 0.0f, 1.0f);
                    break;
                }
                case 3: {
                    Matrix4f.rotate(p_178407_2_.angle * 0.017453292f, new Vector3f(0.0f, 0.0f, 1.0f), var3, var3);
                    var4.set(1.0f, 1.0f, 0.0f);
                    break;
                }
            }
            if (p_178407_2_.rescale) {
                if (Math.abs(p_178407_2_.angle) == 22.5f) {
                    var4.scale(FaceBakery.field_178418_a);
                }
                else {
                    var4.scale(FaceBakery.field_178417_b);
                }
                Vector3f.add(var4, new Vector3f(1.0f, 1.0f, 1.0f), var4);
            }
            else {
                var4.set(1.0f, 1.0f, 1.0f);
            }
            this.rotateScale(p_178407_1_, new Vector3f((ReadableVector3f)p_178407_2_.origin), var3, var4);
        }
    }
    
    public int rotateVertex(final Vector3f position, final EnumFacing facing, final int vertexIndex, final ModelRotation modelRotationIn, final boolean uvLocked) {
        return this.rotateVertex(position, facing, vertexIndex, (ITransformation)modelRotationIn, uvLocked);
    }
    
    public int rotateVertex(final Vector3f position, final EnumFacing facing, final int vertexIndex, final ITransformation modelRotationIn, final boolean uvLocked) {
        if (modelRotationIn == ModelRotation.X0_Y0) {
            return vertexIndex;
        }
        if (Reflector.ForgeHooksClient_transform.exists()) {
            Reflector.call(Reflector.ForgeHooksClient_transform, position, modelRotationIn.getMatrix());
        }
        else {
            this.rotateScale(position, new Vector3f(0.5f, 0.5f, 0.5f), ((ModelRotation)modelRotationIn).getMatrix4d(), new Vector3f(1.0f, 1.0f, 1.0f));
        }
        return modelRotationIn.rotate(facing, vertexIndex);
    }
    
    private void rotateScale(final Vector3f position, final Vector3f rotationOrigin, final Matrix4f rotationMatrix, final Vector3f scale) {
        final Vector4f var5 = new Vector4f(position.x - rotationOrigin.x, position.y - rotationOrigin.y, position.z - rotationOrigin.z, 1.0f);
        Matrix4f.transform(rotationMatrix, var5, var5);
        final Vector4f vector4f = var5;
        vector4f.x *= scale.x;
        final Vector4f vector4f2 = var5;
        vector4f2.y *= scale.y;
        final Vector4f vector4f3 = var5;
        vector4f3.z *= scale.z;
        position.set(var5.x + rotationOrigin.x, var5.y + rotationOrigin.y, var5.z + rotationOrigin.z);
    }
    
    private Matrix4f getMatrixIdentity() {
        final Matrix4f var1 = new Matrix4f();
        var1.setIdentity();
        return var1;
    }
    
    public static EnumFacing getFacingFromVertexData(final int[] faceData) {
        final int step = faceData.length / 4;
        final int step2 = step * 2;
        final int step3 = step * 3;
        final Vector3f vector3f = new Vector3f(Float.intBitsToFloat(faceData[0]), Float.intBitsToFloat(faceData[1]), Float.intBitsToFloat(faceData[2]));
        final Vector3f vector3f2 = new Vector3f(Float.intBitsToFloat(faceData[step]), Float.intBitsToFloat(faceData[step + 1]), Float.intBitsToFloat(faceData[step + 2]));
        final Vector3f vector3f3 = new Vector3f(Float.intBitsToFloat(faceData[step2]), Float.intBitsToFloat(faceData[step2 + 1]), Float.intBitsToFloat(faceData[step2 + 2]));
        final Vector3f vector3f4 = new Vector3f();
        final Vector3f vector3f5 = new Vector3f();
        final Vector3f vector3f6 = new Vector3f();
        Vector3f.sub(vector3f, vector3f2, vector3f4);
        Vector3f.sub(vector3f3, vector3f2, vector3f5);
        Vector3f.cross(vector3f5, vector3f4, vector3f6);
        final float f = (float)Math.sqrt(vector3f6.x * vector3f6.x + vector3f6.y * vector3f6.y + vector3f6.z * vector3f6.z);
        final Vector3f vector3f8 = vector3f6;
        vector3f8.x /= f;
        final Vector3f vector3f9 = vector3f6;
        vector3f9.y /= f;
        final Vector3f vector3f10 = vector3f6;
        vector3f10.z /= f;
        EnumFacing enumfacing = null;
        float f2 = 0.0f;
        for (final EnumFacing enumfacing2 : EnumFacing.values()) {
            final Vec3i vec3i = enumfacing2.getDirectionVec();
            final Vector3f vector3f7 = new Vector3f((float)vec3i.getX(), (float)vec3i.getY(), (float)vec3i.getZ());
            final float f3 = Vector3f.dot(vector3f6, vector3f7);
            if (f3 >= 0.0f && f3 > f2) {
                f2 = f3;
                enumfacing = enumfacing2;
            }
        }
        if (f2 < 0.719f) {
            if (enumfacing == EnumFacing.EAST || enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH) {
                enumfacing = EnumFacing.NORTH;
            }
            else {
                enumfacing = EnumFacing.UP;
            }
        }
        if (enumfacing == null) {
            return EnumFacing.UP;
        }
        return enumfacing;
    }
    
    public void func_178409_a(final int[] p_178409_1_, final EnumFacing p_178409_2_, final BlockFaceUV p_178409_3_, final TextureAtlasSprite p_178409_4_) {
        for (int var5 = 0; var5 < 4; ++var5) {
            this.func_178401_a(var5, p_178409_1_, p_178409_2_, p_178409_3_, p_178409_4_);
        }
    }
    
    private void applyFacing(final int[] p_178408_1_, final EnumFacing p_178408_2_) {
        final int[] aint = new int[p_178408_1_.length];
        System.arraycopy(p_178408_1_, 0, aint, 0, p_178408_1_.length);
        final float[] afloat = new float[EnumFacing.values().length];
        afloat[EnumFaceDirection.Constants.WEST_INDEX] = 999.0f;
        afloat[EnumFaceDirection.Constants.DOWN_INDEX] = 999.0f;
        afloat[EnumFaceDirection.Constants.NORTH_INDEX] = 999.0f;
        afloat[EnumFaceDirection.Constants.EAST_INDEX] = -999.0f;
        afloat[EnumFaceDirection.Constants.UP_INDEX] = -999.0f;
        afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = -999.0f;
        final int step = p_178408_1_.length / 4;
        for (int i = 0; i < 4; ++i) {
            final int j = step * i;
            final float f = Float.intBitsToFloat(aint[j]);
            final float f2 = Float.intBitsToFloat(aint[j + 1]);
            final float f3 = Float.intBitsToFloat(aint[j + 2]);
            if (f < afloat[EnumFaceDirection.Constants.WEST_INDEX]) {
                afloat[EnumFaceDirection.Constants.WEST_INDEX] = f;
            }
            if (f2 < afloat[EnumFaceDirection.Constants.DOWN_INDEX]) {
                afloat[EnumFaceDirection.Constants.DOWN_INDEX] = f2;
            }
            if (f3 < afloat[EnumFaceDirection.Constants.NORTH_INDEX]) {
                afloat[EnumFaceDirection.Constants.NORTH_INDEX] = f3;
            }
            if (f > afloat[EnumFaceDirection.Constants.EAST_INDEX]) {
                afloat[EnumFaceDirection.Constants.EAST_INDEX] = f;
            }
            if (f2 > afloat[EnumFaceDirection.Constants.UP_INDEX]) {
                afloat[EnumFaceDirection.Constants.UP_INDEX] = f2;
            }
            if (f3 > afloat[EnumFaceDirection.Constants.SOUTH_INDEX]) {
                afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = f3;
            }
        }
        final EnumFaceDirection var17 = EnumFaceDirection.getFacing(p_178408_2_);
        for (int j = 0; j < 4; ++j) {
            final int j2 = step * j;
            final EnumFaceDirection.VertexInformation var18 = var17.func_179025_a(j);
            final float f3 = afloat[var18.field_179184_a];
            final float var19 = afloat[var18.field_179182_b];
            final float var20 = afloat[var18.field_179183_c];
            p_178408_1_[j2] = Float.floatToRawIntBits(f3);
            p_178408_1_[j2 + 1] = Float.floatToRawIntBits(var19);
            p_178408_1_[j2 + 2] = Float.floatToRawIntBits(var20);
            for (int var21 = 0; var21 < 4; ++var21) {
                final int var22 = step * var21;
                final float var23 = Float.intBitsToFloat(aint[var22]);
                final float var24 = Float.intBitsToFloat(aint[var22 + 1]);
                final float var25 = Float.intBitsToFloat(aint[var22 + 2]);
                if (MathHelper.epsilonEquals(f3, var23) && MathHelper.epsilonEquals(var19, var24) && MathHelper.epsilonEquals(var20, var25)) {
                    p_178408_1_[j2 + 4] = aint[var22 + 4];
                    p_178408_1_[j2 + 4 + 1] = aint[var22 + 4 + 1];
                }
            }
        }
    }
    
    private void func_178401_a(final int p_178401_1_, final int[] p_178401_2_, final EnumFacing p_178401_3_, final BlockFaceUV p_178401_4_, final TextureAtlasSprite p_178401_5_) {
        final int step = p_178401_2_.length / 4;
        final int var6 = step * p_178401_1_;
        float var7 = Float.intBitsToFloat(p_178401_2_[var6]);
        float var8 = Float.intBitsToFloat(p_178401_2_[var6 + 1]);
        float var9 = Float.intBitsToFloat(p_178401_2_[var6 + 2]);
        if (var7 < -0.1f || var7 >= 1.1f) {
            var7 -= MathHelper.floor_float(var7);
        }
        if (var8 < -0.1f || var8 >= 1.1f) {
            var8 -= MathHelper.floor_float(var8);
        }
        if (var9 < -0.1f || var9 >= 1.1f) {
            var9 -= MathHelper.floor_float(var9);
        }
        float var10 = 0.0f;
        float var11 = 0.0f;
        switch (SwitchEnumFacing.field_178400_a[p_178401_3_.ordinal()]) {
            case 1: {
                var10 = var7 * 16.0f;
                var11 = (1.0f - var9) * 16.0f;
                break;
            }
            case 2: {
                var10 = var7 * 16.0f;
                var11 = var9 * 16.0f;
                break;
            }
            case 3: {
                var10 = (1.0f - var7) * 16.0f;
                var11 = (1.0f - var8) * 16.0f;
                break;
            }
            case 4: {
                var10 = var7 * 16.0f;
                var11 = (1.0f - var8) * 16.0f;
                break;
            }
            case 5: {
                var10 = var9 * 16.0f;
                var11 = (1.0f - var8) * 16.0f;
                break;
            }
            case 6: {
                var10 = (1.0f - var9) * 16.0f;
                var11 = (1.0f - var8) * 16.0f;
                break;
            }
        }
        final int var12 = p_178401_4_.func_178345_c(p_178401_1_) * step;
        p_178401_2_[var12 + 4] = Float.floatToRawIntBits(p_178401_5_.getInterpolatedU(var10));
        p_178401_2_[var12 + 4 + 1] = Float.floatToRawIntBits(p_178401_5_.getInterpolatedV(var11));
    }
    
    static {
        field_178418_a = 1.0f / (float)Math.cos(0.39269909262657166) - 1.0f;
        field_178417_b = 1.0f / (float)Math.cos(0.7853981633974483) - 1.0f;
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_178400_a;
        static final int[] field_178399_b;
        private static final String __OBFID = "CL_00002489";
        
        static {
            field_178399_b = new int[EnumFacing.Axis.values().length];
            try {
                SwitchEnumFacing.field_178399_b[EnumFacing.Axis.X.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_178399_b[EnumFacing.Axis.Y.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_178399_b[EnumFacing.Axis.Z.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            field_178400_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
        }
    }
}
