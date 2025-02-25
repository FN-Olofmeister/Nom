use burn::tensor::Tensor;
use burn::tensor::Distribution;
use burn_ndarray::NdArray;

#[derive(Debug)]
pub struct Linear {
    weight: Tensor<NdArray, 2>,
    bias: Tensor<NdArray, 1>,
}

impl Linear {
    // 모델을 초기화합니다.
    pub fn new(input_dim: usize, output_dim: usize) -> Self {
        let weight = Tensor::random(
            [input_dim, output_dim],
            Distribution::Uniform(-1.0, 1.0),
        );
        let bias = Tensor::zeros([output_dim]);
        Self { weight, bias }
    }
    
    // 순전파 함수입니다.
    pub fn forward(&self, x: Tensor<NdArray, 2>) -> Tensor<NdArray, 2> {
        x.matmul(self.weight.clone()) + self.bias.clone().unsqueeze()
    }
}

fn main() {
    // 입력 데이터입니다.
    let x = Tensor::<NdArray, 2>::from([[1.0, 2.0, 3.0]]);
    // 실제 값입니다.
    let y_true = Tensor::<NdArray, 2>::from([[10.0]]);
    
    // 모델을 생성합니다.
    let model = Linear::new(3, 1);
    
    // 예측을 수행합니다.
    let y_pred = model.forward(x);
    println!("Predicted output: {:?}", y_pred);
    
    // MSE 손실을 계산합니다.
    let loss = (y_pred - y_true).powf(2.0).mean();
    println!("Loss: {:?}", loss);
}
