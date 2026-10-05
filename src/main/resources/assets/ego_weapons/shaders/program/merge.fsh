#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D MergeSampler;

varying vec2 texCoord;
uniform vec2 OutSize;


void main() {
    vec4 base = texture2D(DiffuseSampler, texCoord);

    // Horizontal Gaussian-ish blur.
    vec4 colA = vec4(0.0);

    vec2 pixelOffset = 1 / OutSize;

    colA += texture2D(MergeSampler, texCoord).rgba;

    // Add bloom on top of the original scene.
    gl_FragColor = vec4(base.rgba);
}