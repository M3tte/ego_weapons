#version 120

uniform sampler2D DiffuseSampler;
uniform sampler2D MaskSampler;

varying vec2 texCoord;
uniform vec2 OutSize;

uniform float DistortionStrength = 55;
uniform float ChromaticStrength = 2;

void main() {

    // Sample mask
    vec4 mask = texture2D(MaskSampler, texCoord);

    // Convert RG from 0→1 into -1→1
    vec2 distortion =
    (mask.rg * 2.0 - 1.0);

    // Distortion amount
    distortion *= DistortionStrength;

    // Convert from pixel space into UV space
    vec2 pixelOffset =
    distortion / OutSize;

    // Blue channel controls aberration strength
    float chromaMask = distance(0.5f, mask.b) * 2;

    // Final chromatic offset
    vec2 chromaOffset =
    pixelOffset *
    chromaMask *
    ChromaticStrength;

    // Main distorted UV
    vec2 uv = texCoord + pixelOffset;

    // Chromatic aberration only where mask.b > 0
    vec2 redUV  = uv + chromaOffset;
    vec2 blueUV = uv - chromaOffset;

    // Sample channels
    float r = texture2D(DiffuseSampler, redUV).r;
    float g = texture2D(DiffuseSampler, uv).g;
    float b = texture2D(DiffuseSampler, blueUV).b;

    float upfactor = 0;

    if (mask.b > 0.50) {
        upfactor = min(distance(0.5, mask.b),0.25f);
    }


    gl_FragColor = vec4(r + upfactor, g + upfactor, b + upfactor, 1.0);
}